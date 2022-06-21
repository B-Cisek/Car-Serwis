package car.serwis.helpers;

import car.serwis.database.model.Pracownik;
import javafx.scene.text.Text;

/**
 * Klasa do zarządzania aktualnym użytkownikiem
 */
public class CurrentPracownik {
    private static Pracownik pracownik; /** Pole statyczne obiektu Pracownik */

    /**
     * Konstruktor bezparametrowy
     */
    private CurrentPracownik() {
    }

    /**
     * Metoda zwracająca aktualnie zalogowanego użytkownika
     *
     * @return zwraca obiekt pracownik
     */
    public static Pracownik getCurrentPracownik() {
        return pracownik;
    }

    /**
     * Metoda ustawiająca aktualnego użytkownika
     *
     * @param currentPracownik aktualny użytkownik
     */
    public static void setCurrentPracownik(Pracownik currentPracownik) {
        pracownik = currentPracownik;
    }

    /**
     * Metoda wyświetlająca login użytkownika
     *
     * @param text Text.class
     */
    public static void setPracownikInfo(Text text) {
        text.setText(String.format("Pracownik: %s", CurrentPracownik.getCurrentPracownik().getLogin()));
    }
}
