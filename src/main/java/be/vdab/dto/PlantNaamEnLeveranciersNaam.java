package be.vdab.dto;

public class PlantNaamEnLeveranciersNaam {
    private final String plantNaam;
    private final String leveranciersNaam;

    public PlantNaamEnLeveranciersNaam(String plantNaam, String leveranciersNaam) {
        this.plantNaam = plantNaam;
        this.leveranciersNaam = leveranciersNaam;
    }

    @Override
    public String toString() {
        return plantNaam + " - " + leveranciersNaam;
    }
}
