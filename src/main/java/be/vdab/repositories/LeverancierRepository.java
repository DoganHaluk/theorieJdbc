package be.vdab.repositories;

import be.vdab.domain.Leverancier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LeverancierRepository extends AbstractRepository {
    public List<String> findAllNamen() throws SQLException {
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(
                     "select naam from leveranciers")) {
            var namen = new ArrayList<String>();
            var result = statement.executeQuery();
            while (result.next()) {
                namen.add(result.getString("naam"));
            }
            return namen;
        }
    }

    public int findAantal() throws SQLException {
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(
                     "select count(*) as aantal from leveranciers")) {
            var result = statement.executeQuery();
            result.next();
            return result.getInt("aantal");
        }
    }

    private Leverancier naarLeverancier(ResultSet result) throws SQLException {
        return new Leverancier(result.getLong("id"), result.getString("naam"), result.getString("adres"), result.getInt("postcode"), result.getString("woonplaats"), result.getObject("sinds", LocalDate.class));
    }

    public List<Leverancier> findAll() throws SQLException {
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(
                     "select id,naam,adres,postcode,woonplaats,sinds from leveranciers")) {
            var leveranciers = new ArrayList<Leverancier>();
            var result = statement.executeQuery();
            while (result.next()) {
                leveranciers.add(naarLeverancier(result));
            }
            return leveranciers;
        }
    }

    public List<Leverancier> findByWoonplaats(String woonplaats) throws SQLException {
        var sql =
                "select id,naam,adres,postcode,woonplaats,sinds from leveranciers where woonplaats = ?";
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setString(1, woonplaats);
            var leveranciers = new ArrayList<Leverancier>();
            var result = statement.executeQuery();
            while (result.next()) {
                leveranciers.add(naarLeverancier(result));
            }
            return leveranciers;
        }
    }

    public Optional<Leverancier> findById(long id) throws SQLException {
        var sql =
                "select id,naam,adres,postcode,woonplaats,sinds from leveranciers where id = ?";
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            return result.next() ? Optional.of(naarLeverancier(result))
                    : Optional.empty();
        }
    }
}
