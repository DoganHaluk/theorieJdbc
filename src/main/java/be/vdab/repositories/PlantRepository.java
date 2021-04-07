package be.vdab.repositories;

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
}
