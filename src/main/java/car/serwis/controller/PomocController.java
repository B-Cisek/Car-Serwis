package car.serwis.controller;

import car.serwis.helpers.CurrentPracownik;
import car.serwis.helpers.WindowManagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PomocController implements Initializable {
    @FXML
    private Button exitButton;

    @FXML
    private Button minimalizeButton;

    @FXML
    private BorderPane pomocBorderPane;

    @FXML
    private Text pracownikInfo;


    WindowManagement windowManagement = new WindowManagement();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        windowManagement.initializeMinimalizeButton(minimalizeButton, pomocBorderPane);
        windowManagement.initializeExitButton(exitButton, pomocBorderPane);
        CurrentPracownik.setPracownikInfo(pracownikInfo);
    }


    @FXML
    void showPulpitScreen(ActionEvent event) throws IOException {
        SceneController.getPulpitScene(event);
    }

    @FXML
    void showZleceniaScreen(ActionEvent event) throws IOException {
        SceneController.getZleceniaScene(event);
    }

    @FXML
    void showWarsztatScreen(ActionEvent event) throws IOException {
        SceneController.getWarsztatScene(event);
    }

    @FXML
    void showKsiegowoscScreen(ActionEvent event) throws IOException {
        SceneController.getKsiegowoscScene(event);
    }

    @FXML
    void showMagazynScreen(ActionEvent event) throws IOException {
        SceneController.getMagazynScene(event);
    }

    @FXML
    void showUstawieniaScreen(ActionEvent event) throws IOException {
        SceneController.getUstawieniaScene(event);
    }

}
