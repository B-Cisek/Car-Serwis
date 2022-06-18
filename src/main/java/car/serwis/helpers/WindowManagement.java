package car.serwis.helpers;

import car.serwis.controller.ShowZlecenieController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

public class WindowManagement {
    public void initializeMinimalizeButton(Button button, BorderPane borderPane){
        button.setOnAction((x) -> {
            Stage stage = (Stage) borderPane.getScene().getWindow();
            stage.setIconified(true);
        });
    }

    public void initializeExitButton(Button button, BorderPane borderPane){
        button.setOnAction((x) -> {
            Stage stage = (Stage) borderPane.getScene().getWindow();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdzenie");
            alert.setHeaderText("Czy na pewno chcesz zamknąć aplikacje?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                stage.close();
            }
        });
    }

    public void initializeExitButtonAnchorPane(Button button, AnchorPane anchorPane){
        button.setOnAction((x) -> {
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
        });
    }
}
