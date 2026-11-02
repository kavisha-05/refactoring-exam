package fr.efrei.residence;

public final class StrategiesProfil {
    private static final StrategieProfil STANDARD = new StrategieStandard();
    private static final StrategieProfil BOURSIER = new StrategieBoursier();
    private static final StrategieProfil ALTERNANT = new StrategieAlternant();
    private static final StrategieProfil INTERNATIONAL = new StrategieInternational();

    private StrategiesProfil() { }

    public static StrategieProfil pour(Profil profil) {
        if (profil == null) return STANDARD;
        return switch (profil) {
            case STANDARD -> STANDARD;
            case BOURSIER -> BOURSIER;
            case ALTERNANT -> ALTERNANT;
            case INTERNATIONAL -> INTERNATIONAL;
        };
    }
}
