package fr.efrei.residence;

import java.util.List;

public interface JournalNotifications {
    void enregistrer(NotificationEnvoyee notification, double affranchissement);
    List<NotificationEnvoyee> consulter();
    int nombreSms();
    double coutTimbres();
}
