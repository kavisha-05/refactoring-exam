package fr.efrei.residence;

import java.util.Set;

public class CalculateurLoyer {

    private static final double COEFFICIENT_MEUBLE = 1.15;
    private static final double COEFFICIENT_REZ_DE_CHAUSSEE = 0.95;
    private static final double PRIX_PARKING = 45;
    private static final double PRIX_LAVERIE = 12;
    private static final double PRIX_ASSURANCE = 9;

    /**
     * Loyer mensuel hors charges, options comprises, remise déduite.
     */
    public double calculerLoyer(Logement l, Profil profil, int echelon, Set<Option> options) {
        StrategieLoyer strategie = StrategiesLoyer.pour(l.getType());
        double loyer = strategie.calculerBase(l);

        if (l.isMeuble()) loyer = loyer * COEFFICIENT_MEUBLE;
        if (l.getEtage() == Logement.REZ_DE_CHAUSSEE) loyer = loyer * COEFFICIENT_REZ_DE_CHAUSSEE;

        if (options.contains(Option.MENAGE)) {
            loyer = loyer * strategie.coefficientMenage();
        }
        if (options.contains(Option.PARKING)) loyer = loyer + PRIX_PARKING;
        if (options.contains(Option.LAVERIE)) loyer = loyer + PRIX_LAVERIE;
        if (options.contains(Option.FIBRE)) loyer = loyer + strategie.prixFibre();
        if (options.contains(Option.ASSURANCE)) loyer = loyer + PRIX_ASSURANCE;

        loyer = StrategiesProfil.pour(profil).appliquerRemise(loyer, echelon);

        return Utils.arrondir(loyer);
    }

    public double calculerCharges(Logement l) {
        return StrategiesLoyer.pour(l.getType()).charges();
    }
}
