package fr.efrei.residence;

import java.util.Map;

public final class FabriqueNotification implements CanauxNotification {
    private final Map<Canal, CanalNotification> canaux;

    public FabriqueNotification(Map<Canal, CanalNotification> canaux) {
        this.canaux = Map.copyOf(canaux);
    }

    @Override
    public CanalNotification creer(Canal canal) {
        CanalNotification notification = canaux.get(canal);
        if (notification == null) throw new IllegalArgumentException("Canal inconnu " + canal);
        return notification;
    }
}
