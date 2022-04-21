package car.serwis.helpers;

import car.serwis.database.model.Pracownik;

public class CurrentPracownik {
    private static Pracownik pracownik;

    private CurrentPracownik() {
    }

    public static Pracownik getCurrentPracownik() {
        return pracownik;
    }

    public static void setCurrentPracownik(Pracownik currentPracownik) {
        pracownik = currentPracownik;
    }
}
