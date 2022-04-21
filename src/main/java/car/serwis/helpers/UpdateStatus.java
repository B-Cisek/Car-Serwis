package car.serwis.helpers;

public final class UpdateStatus {

    private UpdateStatus(){
    }

    private static boolean isStanowiskoAdded;
    private static boolean isPracownikAdded;

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


}
