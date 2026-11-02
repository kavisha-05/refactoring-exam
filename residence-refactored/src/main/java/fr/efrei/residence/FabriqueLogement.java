package fr.efrei.residence;

import java.util.Map;

public class FabriqueLogement {

    private static final int PLACES_COLOCATION_MIN = 2;

    @FunctionalInterface
    private interface CreationLogement {
        Logement creer(String numero, double surface, int etage, boolean meuble, int param);

        default Logement creerPourAjout(String numero, double surface, int etage, boolean meuble, int param) {
            return creer(numero, surface, etage, meuble, param);
        }
    }

    private static final Map<TypeLogement, CreationLogement> CREATIONS = Map.of(
            TypeLogement.STUDIO, (numero, surface, etage, meuble, param) -> new Studio(numero, surface, etage, meuble),
            TypeLogement.T1, (numero, surface, etage, meuble, param) -> new T1(numero, surface, etage, meuble),
            TypeLogement.T2, (numero, surface, etage, meuble, param) -> new T2(numero, surface, etage, meuble, param),
            TypeLogement.COLOCATION, new CreationLogement() {
                @Override
                public Logement creer(String numero, double surface, int etage, boolean meuble, int param) {
                    return new Colocation(numero, surface, etage, meuble, param);
                }

                @Override
                public Logement creerPourAjout(String numero, double surface, int etage, boolean meuble, int param) {
                    return creer(numero, surface, etage, meuble, Math.max(param, PLACES_COLOCATION_MIN));
                }
            });

    public static Logement creer(TypeLogement type, String numero, double surface,
                                 int etage, boolean meuble, int param) {
        if (type == null) {
            return null;
        }
        return CREATIONS.get(type).creer(numero, surface, etage, meuble, param);
    }

    public static Logement creerPourAjout(TypeLogement type, String numero, double surface,
                                         int etage, boolean meuble, int param) {
        if (type == null) {
            return null;
        }
        return CREATIONS.get(type).creerPourAjout(numero, surface, etage, meuble, param);
    }
}
