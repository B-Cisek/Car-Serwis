package car.serwis.controller;

import car.serwis.helpers.CurrentPracownik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PulpitController implements Initializable {

    @FXML
    private Button exitButton;

    @FXML
    private BorderPane pulpitBorderPane;

    @FXML
    private Text pracownikInfo;



    private void initializeExitButton(){
        exitButton.setOnAction((x) -> {
            getStage().close();
        });
    }

    private Stage getStage(){
        return (Stage) pulpitBorderPane.getScene().getWindow();
    }



    private void setUserInfo() {
        pracownikInfo.setText(String.format("Pracownik: %s", CurrentPracownik.getCurrentPracownik().getLogin()));
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

    @FXML
    void showPomocScreen(ActionEvent event) throws IOException {
        SceneController.getPomocScene(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeExitButton();
        setUserInfo();
    }
}
