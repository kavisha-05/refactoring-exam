package fr.efrei.residence;

import java.util.Set;

public class AssemblageLoyer {
    public Loyer assembler(Logement logement, Set<Option> options) {
        StrategieLoyer strategie = StrategiesLoyer.pour(logement.getType());
        Loyer loyer = new LoyerBase(logement, strategie);

        if (logement.isMeuble()) loyer = new LoyerMeuble(loyer);
        if (logement.getEtage() == Logement.REZ_DE_CHAUSSEE) loyer = new LoyerRezDeChaussee(loyer);
        if (options.contains(Option.MENAGE)) loyer = new LoyerMenage(loyer, strategie.coefficientMenage());
        if (options.contains(Option.PARKING)) loyer = new LoyerParking(loyer);
        if (options.contains(Option.LAVERIE)) loyer = new LoyerLaverie(loyer);
        if (options.contains(Option.FIBRE)) loyer = new LoyerFibre(loyer, strategie.prixFibre());
        if (options.contains(Option.ASSURANCE)) loyer = new LoyerAssurance(loyer);

        return loyer;
    }
}
