package fr.efrei.residence;


public class CourrierService implements CanalNotification {

    public static final double PRIX_TIMBRE = 1.29;
    private final JournalNotifications journal;

    public CourrierService(JournalNotifications journal) {
        this.journal = journal;
    }

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
        journal.enregistrer(new NotificationEnvoyee(Canal.COURRIER, adresse, objet), PRIX_TIMBRE);
        System.out.println("[COURRIER] " + nom + ", " + adresse + " | Objet : " + objet);
        System.out.println("        " + texte);
    }
}
