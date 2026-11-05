package fr.efrei.residence;

import java.util.Map;

public final class FabriqueNotification implements CanauxNotification {
    private final Map<Canal, CanalNotification> canaux;

    public FabriqueNotification(Map<Canal, CanalNotification> canaux) {
        this.canaux = Map.copyOf(canaux);
    }

    public static FabriqueNotification avecCanauxStandards(JournalNotifications journal) {
        return new FabriqueNotification(Map.of(
                Canal.EMAIL, new EmailService(journal),
                Canal.SMS, new SmsService(journal),
                Canal.COURRIER, new CourrierService(journal)));
    }

    @Override
    public CanalNotification creer(Canal canal) {
        CanalNotification notification = canaux.get(canal);
        if (notification == null) throw new IllegalArgumentException("Canal inconnu " + canal);
        return notification;
    }
}
