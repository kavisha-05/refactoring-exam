package fr.efrei.residence;

public class StrategieInternational extends StrategieStandard {
    private static final int MOIS_CAUTION = 2;
    private static final double FRAIS_DOSSIER = 150;

    @Override public double calculerCaution(double loyerHorsCharges) { return loyerHorsCharges * MOIS_CAUTION; }
    @Override public double fraisDossier() { return FRAIS_DOSSIER; }
    @Override public boolean telephoneValide(String telephone) { return telephone != null && telephone.startsWith("+"); }
    @Override public boolean emailSupplementaire() { return true; }
}
