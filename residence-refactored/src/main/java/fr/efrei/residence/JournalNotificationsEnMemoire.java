package fr.efrei.residence;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JournalNotificationsEnMemoire implements JournalNotifications {
    private final List<NotificationEnvoyee> notifications = new ArrayList<>();
    private int nombreSms;
    private double coutTimbres;

    @Override
    public void enregistrer(NotificationEnvoyee notification, double affranchissement) {
        notifications.add(notification);
        if (notification.canal() == Canal.SMS) nombreSms++;
        coutTimbres += affranchissement;
    }

    @Override
    public List<NotificationEnvoyee> consulter() {
        return notifications.stream().sorted(Comparator.comparing(NotificationEnvoyee::canal)).toList();
    }

    @Override public int nombreSms() { return nombreSms; }
    @Override public double coutTimbres() { return coutTimbres; }
}
