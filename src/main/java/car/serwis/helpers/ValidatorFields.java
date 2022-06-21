package car.serwis.helpers;

/**
 * Klasa implementująca walidacje pól
 */
public abstract class ValidatorFields {
    /**
     * Metoda sprawdza czy podany string jest pusty
     * @param textField string z TextField
     * @return zwraca true gdy pusty string
     */
    public static boolean isBlank(String textField){
        if (textField.isBlank()){
            return true;
        }
        return false;
    }

    /**
     * Metoda sprawdza czy podany string jest textem z polskimi znakami i spacjami
     * @param textField string z TextField
     * @return zwraca true gdy walidacja poprawna
     */
    public static boolean isText(String textField){
        if (textField.matches("^[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ\\s+]+$")){
            return true;
        }
        return false;
    }

    /**
     * Metoda sprawdza czy podany string jest liczbą
     * @param textField string z TextField
     * @return zwraca true gdy walidacja poprawna
     */
    public static boolean isNumeric(String textField){
        if (textField.matches("[0-9]+")){
            return true;
        }
        return false;
    }

    /**
     * Metoda sprawdza czy podany string jest poprawnym hasłem
     * -Litery [a-z] [A-D]
     * -liczby [0-9]
     * -brak spacji
     * -minimum 5 znaków
     * -maksimum 20 znaków
     * @param textField string z TextField
     * @return zwraca true gdy gdy hasło poprawne
     */
    public static boolean isCorrectPassword(String textField){
        if (textField.matches("^[A-Za-z!@#$*\\d]{5,20}$")){
            return true;
        }
        return false;
    }

    /**
     * Metoda sprawdza czy podany string jest poprawnym nipem
     * @param textField string z TextField
     * @return zwraca true gdy walidacja poprawna
     */
    public static boolean isNip(String textField){
        if (textField.matches("^\\d{9}$")){
            return true;
        }
        return false;
    }

    /**
     * Metoda sprawdza czy podany string jest poprawnym peselem
     * @param textField string z TextField
     * @return zwraca true gdy walidacja poprawna
     */
    public static boolean isPesel(String textField){
        if (textField.matches("^\\d{11}$")){
            return true;
        }
        return false;
    }

    /**
     * Metoda sprawdza czy podany string jest poprawnym kodem pocztowym ##-###
     * @param textField string z TextField
     * @return zwraca true gdy walidacja poprawna
     */
    public static boolean isKodPocztowy(String textField){
        if (textField.matches("^[0-9]{2}(?:-[0-9]{3})?$")){
            return true;
        }
        return false;
    }

    /**
     * Metoda sprawdza czy podany string jest liczbą zmiennoprzecinkową
     * @param textField string z TextField
     * @return zwraca true gdy walidacja poprawna
     */
    public static boolean isDecimal(String textField){
        if (textField.matches("^\\d+\\.?\\d*?$")){
            return true;
        }
        return false;
    }

    /**
     * Metoda sprawdza czy podany string jest poprawnym numerem faktury
     * @param textField string z TextField
     * @return zwraca true gdy walidacja poprawna
     */
    public static boolean isNumerFaktury(String textField){
        if (textField.matches("\\d{3}$")){
            return true;
        }
        return false;
    }
}
