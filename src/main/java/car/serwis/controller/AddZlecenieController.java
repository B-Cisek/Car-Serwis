package car.serwis.controller;

import car.serwis.database.dao.*;
import car.serwis.database.model.*;
import car.serwis.helpers.UpdateStatus;
import car.serwis.helpers.ZlecenieStatus;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private ComboBox<Samochod> samochodComboBox;

    @FXML
    private TextArea opisTextArea;

    @FXML
    private ComboBox<ZlecenieStatus> statusComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeExitButton();
        samochodComboBox.setItems(getSamochodObservableList());
        klientComboBox.setItems(getKontrahentObservableList());
        statusComboBox.setItems(getStatusObservableList());
        saveNewCzesc();
    }

    private ObservableList<Samochod> getSamochodObservableList() {
        ObservableList<Samochod> list = FXCollections.observableArrayList();
        list.addAll(new SamochodDao().getSamochody());
        return list;
    }

    private ObservableList<Kontrahent> getKontrahentObservableList() {
        ObservableList<Kontrahent> list = FXCollections.observableArrayList();
        list.addAll(new KontrahentDao().getKontrahent());
        return list;
    }

    private ObservableList<ZlecenieStatus> getStatusObservableList() {
        ObservableList<ZlecenieStatus> list = FXCollections.observableArrayList();
        list.addAll(ZlecenieStatus.NOWE);
        list.addAll(ZlecenieStatus.OCZEKUJACE);
        list.addAll(ZlecenieStatus.W_TRAKCIE);
        list.addAll(ZlecenieStatus.GOTOWE);
        return list;
    }

    private void saveNewCzesc() {
        addZlecenieButton.setOnAction((event) -> {
            if (validateInputs()) {
                Zlecenie zlecenie = createZlecenieFromInput();
                boolean isSaved = new ZlecenieDao().createZlecenie(zlecenie);

                if (isSaved) {
                    UpdateStatus.setIsZlecenieAdded(true);
                    errorText.setText("Zlecenie dodane!");
                    errorText.setStyle("-fx-text-fill: #2CC97E; -fx-font-size: 15px;");
                    delayWindowClose(event);
                }
            }
        });
    }

    private boolean validateInputs() {
        if (dataPrzyjeciaDatePicker.getValue() == null) {
            errorText.setText("*Pole data nie może być puste!");
            return false;
        }

        if (statusComboBox.getValue() == null){
            errorText.setText("*Pole status nie może być puste!");
            return false;
        }

        if (klientComboBox.getValue() == null){
            errorText.setText("*Pole klient nie może być puste!");
            return false;
        }

        if (samochodComboBox.getValue() == null){
            errorText.setText("*Pole samochod nie może być puste!");
            return false;
        }

        if (opisTextArea.getText().equals("")){
            errorText.setText("*Pole opis nie może być puste!");
            return false;
        }

        return true;
    }

    private Zlecenie createZlecenieFromInput() {
        Zlecenie zlecenie = new Zlecenie();

        zlecenie.setDataPrzyjecia(dataPrzyjeciaDatePicker.getValue());
        zlecenie.setOpisZlecenie(opisTextArea.getText());
        zlecenie.setStatus(statusComboBox.getValue());
        zlecenie.setDataPrzyjecia(dataPrzyjeciaDatePicker.getValue());
        zlecenie.setKontrahent(klientComboBox.getValue());
        zlecenie.setSamochod(samochodComboBox.getValue());




        return zlecenie;
    }

    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private Stage getStage(){
        return (Stage) addZlecenieAnchorePane.getScene().getWindow();
    }

    private void initializeExitButton(){
        anulujButton.setOnAction((x) -> {
            getStage().close();
        });
    }
}
