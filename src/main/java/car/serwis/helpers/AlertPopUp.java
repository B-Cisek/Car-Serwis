package car.serwis.helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ProgressBar;

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

    public static void ProgressBarPopUp(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dialogPane = new DialogPane();
        ProgressBar pb = new ProgressBar();
        dialogPane.setContent(pb);
        alert.setDialogPane(dialogPane);
        alert.setTitle("Informacja");
        alert.setContentText(text);
        alert.showAndWait();
    }
}
