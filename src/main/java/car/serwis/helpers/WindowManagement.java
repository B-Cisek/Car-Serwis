package car.serwis.helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Klasa zarządzająca oknem
 */
public class WindowManagement {
    /**
     * Metoda minimalizująca okno po naciśnieciu na przycisk
     * @param button przycisk wykonujący metode
     * @param borderPane borderPane minimalizowanego okna
     */
    public void initializeMinimalizeButton(Button button, BorderPane borderPane) {
        button.setOnAction((x) -> {
            Stage stage = (Stage) borderPane.getScene().getWindow();
            stage.setIconified(true);
        });
    }

    /**
     * Metoda zamykająca okno po naciśnieciu na przycisk
     * @param button przycisk wykonujący metode
     * @param borderPane borderPane zamykanego okna
     */
    public void initializeExitButton(Button button, BorderPane borderPane) {
        button.setOnAction((x) -> {
            Stage stage = (Stage) borderPane.getScene().getWindow();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdzenie");
            alert.setHeaderText("Czy na pewno chcesz zamknąć aplikacje?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                stage.close();
            }
        });
    }

    /**
     * Metoda zamykająca okno po naciśnieciu na przycisk
     * @param button przycisk wykonujący metode
     * @param anchorPane anchorPane zamykanego okna
     */
    public void initializeExitButtonAnchorPane(Button button, AnchorPane anchorPane) {
        button.setOnAction((x) -> {
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();
        });
    }
}
