package be.vdab.repositories;

import be.vdab.exceptions.SoortBestaatAlException;

import java.sql.Connection;
import java.sql.SQLException;

public class SoortRepository extends AbstractRepository {
    public void create(String naam) throws SQLException {
        try (var connection = super.getConnection();
             var statementSelect = connection.prepareStatement(
                     "select id from soorten where naam = ?")) {
            statementSelect.setString(1, naam);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            if (statementSelect.executeQuery().next()) {
                connection.commit();
                throw new SoortBestaatAlException();
            } else {
                try (var statementInsert = connection.prepareStatement(
                        "insert into soorten(naam) values (?)")) {
                    statementInsert.setString(1, naam);
                    statementInsert.executeUpdate();
                    connection.commit();
                }
            }
        }
    }
}
