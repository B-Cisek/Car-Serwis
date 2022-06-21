package car.serwis.helpers;

/**
 * Klasa przechowująca statusy operacji na modelach
 */
public final class UpdateStatus {

    /**
     * Konstruktor bezparametrowy
     */
    private UpdateStatus() {
    }

    /** Pole boolean czy stanowisko dodane?  */
    private static boolean isStanowiskoAdded;

    /** Pole boolean czy pracownik dodane?  */
    private static boolean isPracownikAdded;

    /** Pole boolean czy samochód dodany?  */
    private static boolean isSamochodAdded;

    /** Pole boolean czy kategoria dodana?  */
    private static boolean isKategoriaAdded;

    /** Pole boolean czy jednostka dodana?  */
    private static boolean isJednostkaAdded;

    /** Pole boolean czy kontrahent dodany?  */
    private static boolean isKontrahentAdded;

    /** Pole boolean czy zlecenie dodane?  */
    private static boolean isZlecenieAdded;

    /** Pole boolean czy nowa część dodanea?  */
    private static boolean isCzescAdded;

    /** Pole boolean czy status zlecenia zmieniony dodane?  */
    private static boolean isStatusUpdated;

    /** Pole boolean czy faktura dodana?  */
    private static boolean isFakturaAdded;

    /** Pole boolean czy część dodana?  */
    private static boolean isCzescUpdated;

    public static boolean isStanowiskoAdded() {
        return isStanowiskoAdded;
    }

    public static void setIsStanowiskoAdded(boolean isStanowiskoAdded) {
        UpdateStatus.isStanowiskoAdded = isStanowiskoAdded;
    }

    public static boolean isPracownikAdded() {
        return isPracownikAdded;
    }

    public static void setIsPracownikAdded(boolean isPracownikAdded) {
        UpdateStatus.isPracownikAdded = isPracownikAdded;
    }


    public static boolean isSamochodAdded() {
        return isSamochodAdded;
    }

    public static void setIsSamochodAdded(boolean isSamochodAdded) {
        UpdateStatus.isSamochodAdded = isSamochodAdded;
    }

    public static boolean isKategoriaAdded() {
        return isKategoriaAdded;
    }

    public static void setIsKategoriaAdded(boolean isKategoriaAdded) {
        UpdateStatus.isKategoriaAdded = isKategoriaAdded;
    }

    public static boolean isJednostkaAdded() {
        return isJednostkaAdded;
    }

    public static void setIsJednostkaAdded(boolean isJednostkaAdded) {
        UpdateStatus.isJednostkaAdded = isJednostkaAdded;
    }

    public static boolean isKontrahentAdded() {
        return isKontrahentAdded;
    }

    public static void setIsKontrahentAdded(boolean isKontrahentAdded) {
        UpdateStatus.isKontrahentAdded = isKontrahentAdded;
    }

    public static boolean isZlecenieAdded() {
        return isZlecenieAdded;
    }

    public static void setIsZlecenieAdded(boolean isZlecenieAdded) {
        UpdateStatus.isZlecenieAdded = isZlecenieAdded;
    }

    public static boolean isCzescAdded() {
        return isCzescAdded;
    }

    public static void setIsCzescAdded(boolean isCzescAdded) {
        UpdateStatus.isCzescAdded = isCzescAdded;
    }

    public static boolean isStatusUpdated() {
        return isStatusUpdated;
    }

    public static void setIsStatusUpdated(boolean isStatusUpdated) {
        UpdateStatus.isStatusUpdated = isStatusUpdated;
    }

    public static boolean isFakturaAdded() {
        return isFakturaAdded;
    }

    public static void setIsFakturaAdded(boolean isFakturaAdded) {
        UpdateStatus.isFakturaAdded = isFakturaAdded;
    }

    public static boolean isCzescUpdated() {
        return isCzescUpdated;
    }

    public static void setIsCzescUpdated(boolean isCzescUpdated) {
        UpdateStatus.isCzescUpdated = isCzescUpdated;
    }



}
