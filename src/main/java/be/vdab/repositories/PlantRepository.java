package be.vdab.repositories;

import be.vdab.exceptions.PlantNietGevondenException;
import be.vdab.exceptions.PrijsTeLaagException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlantRepository extends AbstractRepository {
    public int verhoogPrijzenMet10Procent() throws SQLException {
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(
                     "update planten set prijs = prijs * 1.1")) {
            return statement.executeUpdate();
        }
    }

    public int verhoogPrijzenMet10ProcentByNaam(String naam) throws SQLException {
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(
                     "update planten set prijs = prijs * 1.1 where naam = ?")) {
            statement.setString(1, naam);
            return statement.executeUpdate();
        }
    }

    public List<String> findNamenByWoord(String woord) throws SQLException {
        try (var connection = super.getConnection();
             var statement = connection.prepareCall(
                     "{call PlantNamenMetEenWoord(?)}")) {
            statement.setString(1, '%' + woord + '%');
            var namen = new ArrayList<String>();
            var result = statement.executeQuery();
            while (result.next()) {
                namen.add(result.getString("naam"));
            }
            return namen;
        }
    }

    public int verhoogPrijzenBovenEnOnder100Euro() throws SQLException {
        var sqlVanaf100 = "update planten set prijs = prijs * 1.1 where prijs >= 100";
        var sqlTot100 = "update planten set prijs = prijs * 1.05 where prijs < 100";
        try (var connection = super.getConnection();
             var statementVanaf100 = connection.prepareStatement(sqlVanaf100);
             var statementTot100 = connection.prepareStatement(sqlTot100)) {
            connection.setAutoCommit(false);
            statementVanaf100.executeUpdate();
            statementTot100.executeUpdate();
            connection.commit();
            return statementVanaf100.executeUpdate() + statementTot100.executeUpdate();
        }
    }

    public void verlaagPrijsTotMaximumHelft(long id, BigDecimal nieuwePrijs) throws SQLException {
        var sqlUpdate = "update planten set prijs = ? where id = ? and ? > prijs / 2";
        try (var connection = super.getConnection();
             var statementUpdate = connection.prepareStatement(sqlUpdate)) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            statementUpdate.setBigDecimal(1, nieuwePrijs);
            statementUpdate.setLong(2, id);
            statementUpdate.setBigDecimal(3, nieuwePrijs);
            var aantalAangepast = statementUpdate.executeUpdate();
            if (aantalAangepast == 1) {
                connection.commit();
                return;
            }
            var sqlSelect = "select count(*) as aantal from planten where id = ?";
            try (var statemenSelect = connection.prepareStatement(sqlSelect)) {
                statemenSelect.setLong(1, id);
                var result = statemenSelect.executeQuery();
                result.next();
                if (result.getLong("aantal") == 0) {
                    connection.rollback();
                    throw new PlantNietGevondenException();
                }
                connection.rollback();
                throw new PrijsTeLaagException();
            }
        }
    }
}
