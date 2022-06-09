package car.serwis.helpers;

import javafx.scene.control.Alert;

public class AlertPopUp {

    public static void successAlert(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }
}
