package fr.efrei.residence;

public class ServiceNotifications implements NotificationsResidence {
    private final CanauxNotification canaux;

    public ServiceNotifications(CanauxNotification canaux) {
        this.canaux = canaux;
    }

    @Override
    public void notifier(Etudiant etudiant, String sujet, String message) {
        Canal canal = etudiant.getCanalPrefere();
        if (canal != null) canaux.creer(canal).envoyer(etudiant, sujet, message);
        if (StrategiesProfil.pour(etudiant.getProfil()).emailSupplementaire() && canal != Canal.EMAIL) {
            canaux.creer(Canal.EMAIL).envoyer(etudiant, "[EN] " + sujet, message);
        }
    }
}
