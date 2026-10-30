package fr.efrei.residence;

import java.util.Set;

public class CalculateurLoyer {

    private static final double PRIX_M2_STUDIO = 14;
    private static final double LOYER_MIN_STUDIO = 350;
    private static final double PRIX_M2_T1 = 13;
    private static final double FORFAIT_T1 = 50;
    private static final double PRIX_M2_T2 = 12;
    private static final double FORFAIT_T2 = 100;
    private static final double PRIX_M2_COLOCATION = 11;
    private static final double COEFFICIENT_MEUBLE = 1.15;
    private static final double COEFFICIENT_REZ_DE_CHAUSSEE = 0.95;
    private static final double COEFFICIENT_MENAGE_COLOCATION = 1.04;
    private static final double COEFFICIENT_MENAGE_STANDARD = 1.08;
    private static final double PRIX_PARKING = 45;
    private static final double PRIX_LAVERIE = 12;
    private static final double PRIX_FIBRE = 20;
    private static final double PRIX_ASSURANCE = 9;
    private static final double REMISE_PAR_ECHELON = 0.05;
    private static final double REMISE_BOURSE_MAX = 0.30;
    private static final double COEFFICIENT_ALTERNANT = 0.90;
    private static final double CHARGES_COLOCATION = 25;
    private static final double CHARGES_STANDARD = 35;

    /**
     * Loyer mensuel hors charges, options comprises, remise déduite.
     */
    public double calculerLoyer(Logement l, Profil profil, int echelon, Set<Option> options) {
        double loyer;
        if (l.getType() == TypeLogement.STUDIO) {
            loyer = l.getSurface() * PRIX_M2_STUDIO;
            if (loyer < LOYER_MIN_STUDIO) loyer = LOYER_MIN_STUDIO;
        } else if (l.getType() == TypeLogement.T1) {
            loyer = l.getSurface() * PRIX_M2_T1 + FORFAIT_T1;
        } else if (l.getType() == TypeLogement.T2) {
            loyer = l.getSurface() * PRIX_M2_T2 + FORFAIT_T2;
        } else if (l.getType() == TypeLogement.COLOCATION) {
            loyer = l.getSurface() * PRIX_M2_COLOCATION / l.getNombrePlaces();
        } else {
            throw new IllegalArgumentException("Type de logement inconnu : " + l.getType());
        }

        if (l.isMeuble()) loyer = loyer * COEFFICIENT_MEUBLE;
        if (l.getEtage() == Logement.REZ_DE_CHAUSSEE) loyer = loyer * COEFFICIENT_REZ_DE_CHAUSSEE;

        if (options.contains(Option.MENAGE)) {
            if (l.getType() == TypeLogement.COLOCATION) {
                loyer = loyer * COEFFICIENT_MENAGE_COLOCATION; // parties communes déjà nettoyées
            } else {
                loyer = loyer * COEFFICIENT_MENAGE_STANDARD;
            }
        }
        if (options.contains(Option.PARKING)) loyer = loyer + PRIX_PARKING;
        if (options.contains(Option.LAVERIE)) loyer = loyer + PRIX_LAVERIE;
        if (options.contains(Option.FIBRE) && l.getType() != TypeLogement.COLOCATION) loyer = loyer + PRIX_FIBRE; // fibre incluse en coloc
        if (options.contains(Option.ASSURANCE)) loyer = loyer + PRIX_ASSURANCE;

        if (profil == Profil.BOURSIER) {
            double taux = echelon * REMISE_PAR_ECHELON;
            if (taux > REMISE_BOURSE_MAX) taux = REMISE_BOURSE_MAX;
            loyer = loyer * (1 - taux);
        } else if (profil == Profil.ALTERNANT) {
            loyer = loyer * COEFFICIENT_ALTERNANT;
        }

        return Utils.arrondir(loyer);
    }

    public double calculerCharges(Logement l) {
        if (l.getType() == TypeLogement.COLOCATION) return CHARGES_COLOCATION;
        return CHARGES_STANDARD;
    }
}
