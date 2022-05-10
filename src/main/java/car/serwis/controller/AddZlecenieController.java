package car.serwis.controller;

import car.serwis.database.model.Kontrahent;
import car.serwis.database.model.Samochod;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddZlecenieController implements Initializable {

    @FXML
    private AnchorPane addZlecenieAnchorePane;

    @FXML
    private Button addZlecenieButton;

    @FXML
    private Button anulujButton;

    @FXML
    private DatePicker dataPrzyjeciaDatePicker;

    @FXML
    private Text errorText;

    @FXML
    private ComboBox<Kontrahent> klientComboBox;

    @FXML
    private ComboBox<Samochod> markaComboBox;

    @FXML
    private ComboBox<Samochod> modelComboBox;

    @FXML
    private TextArea opisTextArea;

    @FXML
    private ComboBox<?> statusComboBox;

    private void initializeExitButton(){
        anulujButton.setOnAction((x) -> {
            getStage().close();
        });
    }

    private Stage getStage(){
        return (Stage) addZlecenieAnchorePane.getScene().getWindow();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeExitButton();
    }
}
