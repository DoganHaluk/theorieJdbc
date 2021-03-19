package be.vdab.repositories;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeverancierRepository extends AbstractRepository{
    public List<String> findAllNamen() throws SQLException {
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(
                     "select naam from leveranciers")) {
            var namen = new ArrayList<String>();
            var result = statement.executeQuery();
            while (result.next()) {
                namen.add(result.getString(1));
            }
            return namen;
        }
    }
}
