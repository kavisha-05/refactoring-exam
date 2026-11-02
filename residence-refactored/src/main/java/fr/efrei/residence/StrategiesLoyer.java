package fr.efrei.residence;

public final class StrategiesLoyer {
    private static final StrategieLoyer STUDIO = new StrategieLoyerStudio();
    private static final StrategieLoyer T1 = new StrategieLoyerT1();
    private static final StrategieLoyer T2 = new StrategieLoyerT2();
    private static final StrategieLoyer COLOCATION = new StrategieLoyerColocation();

    private StrategiesLoyer() { }

    public static StrategieLoyer pour(TypeLogement type) {
        if (type == null) {
            throw new IllegalArgumentException("Type de logement inconnu : " + type);
        }
        return switch (type) {
            case STUDIO -> STUDIO;
            case T1 -> T1;
            case T2 -> T2;
            case COLOCATION -> COLOCATION;
        };
    }
}
