package car.serwis.helpers;


public abstract class ValidatorFields {
    public static boolean isBlank(String textField){
        if (textField.isBlank()){
            return true;
        }
        return false;
    }

    public static boolean isText(String textField){
        if (textField.matches("[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]+")){
            return true;
        }
        return false;
    }

    public static boolean isNumeric(String textField){
        if (textField.matches("[0-9]+")){
            return true;
        }
        return false;
    }

    public static boolean isHave8Characters(String textField){
        if (textField.matches("^{7,30}$")){
            return true;
        }
        return false;
    }

    public static boolean isNip(String textField){
        if (textField.matches("^\\d{9}$")){
            return true;
        }
        return false;
    }

    public static boolean isPesel(String textField){
        if (textField.matches("^\\d{11}$")){
            return true;
        }
        return false;
    }

    public static boolean isKodPocztowy(String textField){
        if (textField.matches("^[0-9]{2}(?:-[0-9]{3})?$")){
            return true;
        }
        return false;
    }

    public static boolean isDecimal(String textField){
        if (textField.matches("^\\d+\\.?\\d*?$")){
            return true;
        }
        return false;
    }

    public static boolean isNumerFaktury(String textField){
        if (textField.matches("\\d{3}$")){
            return true;
        }
        return false;
    }
}
