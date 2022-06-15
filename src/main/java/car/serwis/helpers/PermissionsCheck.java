package car.serwis.helpers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PermissionsCheck {
    @FXML
    private Button pulpit;


    public static void checkingPermissionsForUser(){
        if (CurrentPracownik.getCurrentPracownik().getStanowisko().equals("Mechanik")){

        }
    }
}
