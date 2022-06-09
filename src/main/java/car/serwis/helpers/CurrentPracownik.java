package car.serwis.helpers;

import car.serwis.database.model.Pracownik;
import javafx.scene.text.Text;

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

    public static void setPracownikInfo(Text text) {
        text.setText(String.format("Pracownik: %s", CurrentPracownik.getCurrentPracownik().getLogin()));
    }
}
