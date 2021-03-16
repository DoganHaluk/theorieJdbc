package be.vdab.repositories;

import java.sql.SQLException;

public class PlantRepository extends AbstractRepository{
    public int verhoogPrijzenMet10Procent() throws SQLException {
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(
                     "update planten set prijs = prijs * 1.1")) {
            return statement.executeUpdate();
        }
    }
}
