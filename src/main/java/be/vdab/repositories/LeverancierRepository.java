package be.vdab.repositories;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
