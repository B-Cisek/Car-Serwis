package car.serwis.controller;

import car.serwis.database.dao.*;
import car.serwis.database.model.*;
import car.serwis.helpers.AlertPopUp;
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

/**
 * Kontroler widoku "addZlecenie.fxml"
 * klasa odpowiedzialna za dodanie zlecenia do bazy
 */
public class AddZlecenieController implements Initializable {

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
        anulujButton.setOnAction(SceneController::close);
        samochodComboBox.setItems(getSamochodObservableList());
        klientComboBox.setItems(getKontrahentObservableList());
        statusComboBox.setItems(getStatusObservableList());
        createZlecenie();
    }

    /**
     * Metoda pobierająca z bazy obiekty samochód i dodjąca je do ObservableList
     * @return zwraca ObservableList samochodów
     */
    private ObservableList<Samochod> getSamochodObservableList() {
        ObservableList<Samochod> list = FXCollections.observableArrayList();
        list.addAll(new SamochodDao().getSamochody());
        return list;
    }

    /**
     * Metoda pobierająca z bazy obiekty kontrahent i dodjąca je do ObservableList
     * @return zwraca ObservableList kontrahentów
     */
    private ObservableList<Kontrahent> getKontrahentObservableList() {
        ObservableList<Kontrahent> list = FXCollections.observableArrayList();
        list.addAll(new KontrahentDao().getKontrahent());
        return list;
    }

    /**
     * Metoda tworzy ObservableList Typów wyliczeniowych ZlecenieStatus
     * @return ObservableList statusów zlecenia
     */
    private ObservableList<ZlecenieStatus> getStatusObservableList() {
        ObservableList<ZlecenieStatus> list = FXCollections.observableArrayList();
        list.addAll(ZlecenieStatus.NOWE);
        list.addAll(ZlecenieStatus.OCZEKUJACE);
        list.addAll(ZlecenieStatus.W_TRAKCIE);
        list.addAll(ZlecenieStatus.GOTOWE);
        return list;
    }

    /**
     * Metoda nasluchujaca button i dodająca zlecenie do bazy
     */
    private void createZlecenie() {
        addZlecenieButton.setOnAction((event) -> {
            if (validateZlecenieInputs()) {
                Zlecenie zlecenie = createZlecenieFromInput();
                boolean isSaved = new ZlecenieDao().createZlecenie(zlecenie);

                if (isSaved) {
                    UpdateStatus.setIsZlecenieAdded(true);
                    delayWindowClose(event);
                    AlertPopUp.successAlert("Zlecenie dodane!");
                }
            }
        });
    }

    /**
     * Metoda walidująca pola zlecenie
     * @return zwraca true jeżeli walidacja przeszła pomyślnie
     */
    private boolean validateZlecenieInputs() {
        /** Walidacja pola data przyjęcia */
        if (dataPrzyjeciaDatePicker.getValue() == null) {
            errorText.setText("Pole data nie może być puste!");
            return false;
        }

        /** Walidacja pola status */
        if (statusComboBox.getValue() == null){
            errorText.setText("Pole status nie może być puste!");
            return false;
        }

        /** Walidacja pola klient */
        if (klientComboBox.getValue() == null){
            errorText.setText("Pole klient nie może być puste!");
            return false;
        }

        /** Walidacja pola samochód */
        if (samochodComboBox.getValue() == null){
            errorText.setText("Pole samochód nie może być puste!");
            return false;
        }

        /** Walidacja pola opis */
        if (opisTextArea.getText().isBlank()){
            errorText.setText("Pole opis nie może być puste!");
            return false;
        }
        return true;
    }

    /**
     * Metoda tworząca obiekt zlecenie na podstawie pobranych pól z widoku "addZlecenie.fxml"
     * @return zwraca obiekt Zlecenie
     */
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

    /**
     * Metoda zamykająca okno z opóźnieniem po dodaniu zlecenia
     */
    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    /**
     * Metoda zamykająca okno "addZlecenie.fxml"
     */
    @FXML
    private void closeWindow(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
