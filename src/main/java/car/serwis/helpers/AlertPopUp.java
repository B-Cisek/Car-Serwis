package car.serwis.helpers;

import javafx.scene.control.Alert;

public class AlertPopUp {

    public static void successAlert(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(text);
        alert.setContentText(null);
        alert.showAndWait();
    }

    public static void successAlertFaktura(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText("Faktura wygenerowanan!");
        alert.setContentText(text);
        alert.showAndWait();
    }
}
