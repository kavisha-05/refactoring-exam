package fr.efrei.residence;

import java.util.Set;
import java.util.StringJoiner;

public class ServiceQuittance implements GenerateurQuittance {
    private String listerOptions(Set<Option> options) {
        StringJoiner libelles = new StringJoiner(", ");
        // L'ordre de l'enum conserve l'ordre d'affichage historique.
        for (Option option : Option.values()) {
            if (options.contains(option)) libelles.add(option.getLibelle());
        }
        return options.isEmpty() ? "aucune" : libelles.toString();
    }

    /**
     * Quittance de loyer pour un mois payé (format AAAA-MM).
     */
    @Override
    public String generer(Bail bail, String mois) {
        Etudiant etudiant = bail.getEtudiant();
        Logement logement = bail.getLogement();
        Paiement p = null;
        for (Paiement x : bail.paiements) {
            if (x.mois.equals(mois)) p = x;
        }
        if (p == null) {
            return "Aucun paiement pour " + mois;
        }
        String q = "";
        q += "==========================================\n";
        q += "   RÉSIDENCE CAMPUS VERT - QUITTANCE\n";
        q += "==========================================\n";
        q += "Bail      : " + bail.getId() + "\n";
        q += "Locataire : " + etudiant.getNomComplet() + " (" + etudiant.getProfil() + ")\n";
        q += "Logement  : " + logement.getNumero() + " - " + logement.getType() + " " + logement.getSurface() + " m²";
        if (logement.isMeuble()) q += " meublé";
        q += "\n";
        q += "Période   : " + mois + "\n";
        q += "Options   : " + listerOptions(bail.getOptions()) + "\n";
        q += "------------------------------------------\n";
        q += "Loyer HC  : " + Utils.euros(bail.getLoyerHorsCharges()) + "\n";
        q += "Charges   : " + Utils.euros(bail.getCharges()) + "\n";
        if (p.penalite > 0) {
            q += "Pénalité  : " + Utils.euros(p.penalite) + "\n";
        }
        q += "Total dû  : " + Utils.euros(p.du) + "\n";
        q += "Payé le   : " + Utils.date(p.date) + " : " + Utils.euros(p.montant) + "\n";
        if (p.montant < p.du) {
            q += "RESTE DÛ  : " + Utils.euros(Utils.arrondir(p.du - p.montant)) + "\n";
        }
        q += "==========================================";
        return q;
    }
}
