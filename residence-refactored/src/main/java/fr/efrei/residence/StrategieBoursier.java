package fr.efrei.residence;

public class StrategieBoursier extends StrategieStandard {
    private static final double REMISE_PAR_ECHELON = 0.05;
    private static final double REMISE_MAX = 0.30;
    private static final int ECHELON_MIN = 0;
    private static final int ECHELON_MAX = 7;
    private static final int PREAVIS_JOURS = 15;

    @Override public double appliquerRemise(double montant, int echelonBourse) {
        double taux = echelonBourse * REMISE_PAR_ECHELON;
        if (taux > REMISE_MAX) taux = REMISE_MAX;
        return montant * (1 - taux);
    }
    @Override public boolean estSolvable(double ressources, double loyerChargesComprises) { return true; }
    @Override public double calculerCaution(double loyerHorsCharges) { return 0; }
    @Override public double fraisDossier() { return 0; }
    @Override public int dureePreavisJours() { return PREAVIS_JOURS; }
    @Override public double calculerPenaliteRetard(double loyerChargesComprises) { return 0; }
    @Override public boolean echelonValide(int echelon) { return echelon >= ECHELON_MIN && echelon <= ECHELON_MAX; }
}
