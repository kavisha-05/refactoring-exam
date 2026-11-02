package fr.efrei.residence;

public class LoyerBase implements Loyer {
    private final Logement logement;
    private final StrategieLoyer strategie;

    public LoyerBase(Logement logement, StrategieLoyer strategie) {
        this.logement = logement;
        this.strategie = strategie;
    }

    @Override
    public double calculer() {
        return strategie.calculerBase(logement);
    }
}
