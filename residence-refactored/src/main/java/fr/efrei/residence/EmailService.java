package fr.efrei.residence;


public class EmailService implements CanalNotification {

    public static final String SMTP_HOST = "smtp.campusvert.fr";
    public static final String EXPEDITEUR = "gestion@campusvert.fr";

    private final JournalNotifications journal;

    public EmailService(JournalNotifications journal) {
        this.journal = journal;
    }

    @Override
    public void envoyer(Etudiant etudiant, String sujet, String message) {
        envoyer(etudiant.getEmail(), sujet, message);
    }

    @Override
    public void relancer(Bail bail) {
        Etudiant etudiant = bail.getEtudiant();
        envoyer(etudiant, "RELANCE - loyer impayé",
                "Bonjour " + etudiant.getPrenom() + ", il reste " + Utils.euros(bail.soldeDu)
                        + " à régler sur le bail " + bail.getId() + ".");
    }

    public boolean envoyer(String destinataire, String sujet, String corps) {
        try {
            if (!Utils.emailValide(destinataire)) {
                throw new Exception("Adresse invalide");
            }
            System.out.println("[EMAIL] " + EXPEDITEUR + " -> " + destinataire + " | " + sujet);
            System.out.println("        " + corps);
            journal.enregistrer(new NotificationEnvoyee(Canal.EMAIL, destinataire, sujet), 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
