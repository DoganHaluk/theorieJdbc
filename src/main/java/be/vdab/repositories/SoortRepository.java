package be.vdab.repositories;

import be.vdab.exceptions.SoortBestaatAlException;

import java.sql.Connection;
import java.sql.SQLException;

public class SoortRepository extends AbstractRepository {
    public void create(String naam) throws SQLException {
        try (var connection = super.getConnection();
             var statementInsert = connection.prepareStatement(
                     "insert into soorten(naam) values (?)")) {
            statementInsert.setString(1, naam);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            try {
                statementInsert.executeUpdate();
                connection.commit();
            } catch (SQLException ex) {
                try (var statementSelect = connection.prepareStatement(
                        "select id from soorten where naam = ?")) {
                    statementSelect.setString(1, naam);
                    if (statementSelect.executeQuery().next()) {
                        connection.commit();
                        throw new SoortBestaatAlException();
                    }
                    connection.commit();
                    throw ex;
                }
            }
        }
    }
}
