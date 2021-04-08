package be.vdab;

import be.vdab.exceptions.PlantNietGevondenException;
import be.vdab.exceptions.PrijsTeLaagException;
import be.vdab.exceptions.SoortBestaatAlException;
import be.vdab.repositories.LeverancierRepository;
import be.vdab.repositories.PlantRepository;
import be.vdab.repositories.SoortRepository;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
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
        var leverancierId = scanner.nextLong();
        var repository4 = new LeverancierRepository();
        try {
            repository4.findById(leverancierId)
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
            System.out.print(repository1.verhoogPrijzenBovenEnOnder100Euro());
            System.out.println(" planten aangepast.");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }

        System.out.print("Naam:");
        scanner = new Scanner(System.in);
        var soortNaam = scanner.nextLine();
        var repository6 = new SoortRepository();
        try {
            var nieuweId = repository6.create(soortNaam);
            System.out.println("Soort toegevoegd. Het nummer is " + nieuweId);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        } catch (SoortBestaatAlException ex) {
            System.out.println("Soort bestaat al.");
        }

        System.out.print("Datum vanaf (dag/maand/jaar):");
        var formatter = DateTimeFormatter.ofPattern("d/M/y");
        scanner = new Scanner(System.in);
        var datum = LocalDate.parse(scanner.nextLine(), formatter);
        var repository7 = new LeverancierRepository();
        try {
            repository7.findBySindsVanaf(datum).forEach(System.out::println);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }

        var repository8 = new LeverancierRepository();
        try {
            repository8.findLeverancierGewordenInHetJaar2000().forEach(System.out::println);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }

        scanner = new Scanner(System.in);
        System.out.print("Nummer plant:");
        var plantId = scanner.nextLong();
        System.out.print("Nieuwe prijs:");
        var nieuwePrijs = scanner.nextBigDecimal();
        var repository9 = new PlantRepository();
        try {
            repository9.verlaagPrijsTotMaximumHelft(plantId, nieuwePrijs);
            System.out.println("Prijs aangepast");
        } catch (PlantNietGevondenException ex) {
            System.out.println("Plant niet gevonden.");
        } catch (PrijsTeLaagException ex) {
            System.out.println("Prijs te laag.");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }

        var ids = new HashSet<Long>();
        scanner = new Scanner(System.in);
        System.out.print("Nummer plant (0 om te stoppen):");
        for (long id; (id = scanner.nextInt()) != 0; ) {
            ids.add(id);
        }
        var repository10 = new PlantRepository();
        try {
            repository10.findNamenByIds(ids).forEach(System.out::println);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }

        var repository11 = new PlantRepository();
        try {
            repository11.findRodePlantenEnHunLeveranciers().forEach(System.out::println);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
