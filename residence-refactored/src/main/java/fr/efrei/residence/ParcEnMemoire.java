package fr.efrei.residence;

import java.util.ArrayList;
import java.util.List;

public class ParcEnMemoire implements ParcLogements {
    private final List<Logement> logements = new ArrayList<>();

    public ParcEnMemoire() {
        for (String ligne : Residence.CATALOGUE) {
            String[] t = ligne.split(";");
            TypeLogement type;
            try {
                type = TypeLogement.valueOf(t[0]);
            } catch (IllegalArgumentException ex) {
                System.out.println("Type inconnu ignoré : " + t[0]);
                continue;
            }
            Logement logement = FabriqueLogement.creer(type, t[1], Double.parseDouble(t[2]),
                    Integer.parseInt(t[3]), Boolean.parseBoolean(t[4]), Integer.parseInt(t[5]));
            if (logement == null) {
                System.out.println("Type inconnu ignoré : " + type);
                continue;
            }
            logements.add(logement);
        }
    }

    @Override public Logement trouver(String numero) {
        for (Logement logement : logements) {
            if (logement.getNumero().equals(numero)) return logement;
        }
        return null;
    }

    @Override public void ajouter(Logement logement) { logements.add(logement); }
    @Override public List<Logement> tous() { return List.copyOf(logements); }
}
