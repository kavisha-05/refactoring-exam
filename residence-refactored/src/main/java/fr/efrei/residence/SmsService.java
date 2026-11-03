package fr.efrei.residence;


public class SmsService implements CanalNotification {

    private static final int LONGUEUR_NUMERO_MIN = 10;
    private static final int LONGUEUR_MESSAGE_MAX = 160;
    private static final String MARQUE_TRONCATURE = "...";

    private final JournalNotifications journal;

    public SmsService(JournalNotifications journal) {
        this.journal = journal;
    }

    @Override
    public void envoyer(Etudiant etudiant, String sujet, String message) {
        envoyerSms(etudiant.getTelephone(), sujet + " - " + message);
    }

    @Override
    public void relancer(Bail bail) {
        envoyerSms(bail.getEtudiant().getTelephone(),
                "Campus Vert : " + Utils.euros(bail.soldeDu) + " impayés sur " + bail.getId());
    }

    public void envoyerSms(String numero, String message) {
        if (numero == null || numero.length() < LONGUEUR_NUMERO_MIN) {
            System.out.println("[SMS] numéro invalide : " + numero);
            return;
        }
        if (message.length() > LONGUEUR_MESSAGE_MAX) {
            message = message.substring(0, LONGUEUR_MESSAGE_MAX - MARQUE_TRONCATURE.length()) + MARQUE_TRONCATURE;
        }
        journal.enregistrer(new NotificationEnvoyee(Canal.SMS, numero, message), 0);
        System.out.println("[SMS] -> " + numero + " : " + message);
    }
}
