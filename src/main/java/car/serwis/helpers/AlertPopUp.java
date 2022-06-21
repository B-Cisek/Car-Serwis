package car.serwis.helpers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Klasa pomocnicza dostarczająca Okna dialogowe typu alert
 */
public class AlertPopUp {

    /**
     * Metoda wyświetlająca okno informacyjne
     *
     * @param text tekst wyświetlany w oknie
     */
    public static void successAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText(text);
        alert.setContentText(null);
        alert.showAndWait();
    }

    /**
     * Metoda wyświetlająca okno informacyjne dla faktury
     *
     * @param text tekst wyświetlany w oknie
     */
    public static void successAlertFaktura(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacja");
        alert.setHeaderText("Faktura wygenerowanan!");
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Metoda wyświetlająca okno typu ProgressBar
     *
     * @param text tekst wyświetlany w oknie
     * @return zwraca stage okna
     */
    public Stage waitingPopUp(String text) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        VBox pane = new VBox();
        pane.setStyle(waitingPopUpStyle());
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);
        Label label = new Label(text);
        ProgressBar progressBar = new ProgressBar();
        pane.getChildren().addAll(label, progressBar);
        stage.setScene(new Scene(pane, 200, 100));
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }

    /**
     * Style css dla metody waitingPopUp
     *
     * @return zwraca string
     */
    private String waitingPopUpStyle() {
        return "-fx-border-color: #D3D3D3; -fx-border-width: 3px; -fx-border-style: solid;";
    }
}
