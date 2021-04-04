package be.vdab;

import be.vdab.repositories.LeverancierRepository;
import be.vdab.repositories.PlantRepository;

import java.sql.DriverManager;
import java.sql.SQLException;

class Main {
    /*private static final String URL = "jdbc:mysql://localhost/tuincentrum";
    private static final String USER = "root";
    private static final String PASSWORD = "Dorado.7";*/

    public static void main(String[] args) {
        /*try (var connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connectie geopend");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }*/

        var repository1 = new PlantRepository();
        try {
            System.out.print(repository1.verhoogPrijzenMet10Procent());
            System.out.println(" planten aangepast.");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }

        var repository2 = new LeverancierRepository();
        try {
            repository2.findAllNamen().forEach(System.out::println);
            System.out.print("Aantal leveranciers:");
            System.out.println(repository2.findAantal());
            repository2.findAll().forEach(System.out::println);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
