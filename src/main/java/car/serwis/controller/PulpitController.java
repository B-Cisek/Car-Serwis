package car.serwis.controller;

import car.serwis.database.dao.ZlecenieDao;
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

public class PulpitController implements Initializable {
    @FXML
    private Button exitButton;

    @FXML
    private BorderPane pulpitBorderPane;

    @FXML
    private Text pracownikInfo;

    @FXML
    private Button minimalizeButton;

    @FXML
    private Text noweSprawy;

    @FXML
    private Text gotoweSprawy;

    @FXML
    private Text oczekujaceSprawy;

    @FXML
    private Text wTrakcieSprawy;

    WindowManagement windowManagement = new WindowManagement();
    ZlecenieDao zlecenieDao = new ZlecenieDao();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        windowManagement.initializeMinimalizeButton(minimalizeButton, pulpitBorderPane);
        windowManagement.initializeExitButton(exitButton, pulpitBorderPane);
        CurrentPracownik.setPracownikInfo(pracownikInfo);
        setZlecenia();
    }

    private void setZlecenia() {
        noweSprawy.setText(String.format("%s", zlecenieDao.getNoweSprawy()));
        oczekujaceSprawy.setText(String.format("%s", zlecenieDao.getOczekujaceSprawy()));
        wTrakcieSprawy.setText(String.format("%s", zlecenieDao.getWtrakcieSprawy()));
        gotoweSprawy.setText(String.format("%s", zlecenieDao.getGotoweSprawy()));
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

}
