package fr.efrei.residence;

import java.util.ArrayList;
import java.util.List;

public class CourrierService implements CanalNotification {

    public static final double PRIX_TIMBRE = 1.29;
    public static double totalAffranchissement = 0;
    public static List<String[]> journal = new ArrayList<String[]>();

    @Override
    public void envoyer(Etudiant etudiant, String sujet, String message) {
        imprimerEtPoster(etudiant.getNomComplet(), etudiant.getAdresse(), sujet, message);
    }

    @Override
    public void relancer(Bail bail) {
        envoyer(bail.getEtudiant(), "Relance loyer impayé",
                "Il reste " + Utils.euros(bail.soldeDu) + " à régler sur le bail " + bail.getId() + ".");
    }

    public void imprimerEtPoster(String nom, String adresse, String objet, String texte) {
        totalAffranchissement = totalAffranchissement + PRIX_TIMBRE;
        journal.add(new String[]{adresse, objet, texte});
        System.out.println("[COURRIER] " + nom + ", " + adresse + " | Objet : " + objet);
        System.out.println("        " + texte);
    }
}
