package be.vdab;

import be.vdab.repositories.LeverancierRepository;
import be.vdab.repositories.PlantRepository;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

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

        /*var repository1 = new PlantRepository();
        try {
            System.out.print(repository1.verhoogPrijzenMet10Procent());
            System.out.println(" planten aangepast.");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }*/

        var repository2 = new LeverancierRepository();
        try {
            repository2.findAllNamen().forEach(System.out::println);
            System.out.print("Aantal leveranciers:");
            System.out.println(repository2.findAantal());
            repository2.findAll().forEach(System.out::println);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }

        System.out.print("Woonplaats:");
        var scanner = new Scanner(System.in);
        var woonplaats = scanner.nextLine();
        var repository = new LeverancierRepository();
        try {
            repository.findByWoonplaats(woonplaats).forEach(System.out::println);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }

        System.out.print("Naam:");
        scanner = new Scanner(System.in);
        var naam = scanner.nextLine();
        var repository3 = new PlantRepository();
        try {
            System.out.print(repository3.verhoogPrijzenMet10ProcentByNaam(naam));
            System.out.println(" plant(en) aangepast.");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }

        System.out.print("id:");
        scanner = new Scanner(System.in);
        var id = scanner.nextLong();
        var repository4 = new LeverancierRepository();
        try {
            repository4.findById(id)
                    .ifPresentOrElse(System.out::println,
                            () -> System.out.println("Niet gevonden"));
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }

        System.out.print("Woord:");
        scanner = new Scanner(System.in);
        var woord = scanner.nextLine();
        var repository5 = new PlantRepository();
        try {
            repository5.findNamenByWoord(woord).forEach(System.out::println);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }

        var repository1 = new PlantRepository();
        try {
            System.out.print(repository1.verhoogPrijzenBovenEnOnder100€());
            System.out.println(" planten aangepast.");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
