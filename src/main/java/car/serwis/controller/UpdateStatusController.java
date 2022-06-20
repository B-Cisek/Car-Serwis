package car.serwis.controller;

import car.serwis.database.dao.ZlecenieDao;
import car.serwis.database.model.Zlecenie;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Klasa zmieniająca status zlecenia
 */
public class UpdateStatusController implements Initializable {
    @FXML
    private Button anulujButton;

    @FXML
    private ComboBox<Zlecenie> idZlecnieComboBox;

    @FXML
    private ComboBox<ZlecenieStatus> nowyStatusComboBox;

    @FXML
    private Button updateButton;

    @FXML
    private Text errorText;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anulujButton.setOnAction(SceneController::close);
        updateStatus();
        idZlecnieComboBox.setItems(getZlecenieObservableList());
        nowyStatusComboBox.setItems(getStatusObservableList());
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
     * Metoda pobierająca z bazy obiekty zlecenie i dodjąca je do ObservableList
     * @return zwraca ObservableList zleceń
     */
    private ObservableList<Zlecenie> getZlecenieObservableList() {
        ObservableList<Zlecenie> list = FXCollections.observableArrayList();
        list.addAll(new ZlecenieDao().getMechanikZlecenia());
        return list;
    }

    /**
     * Metoda zmieniająca status wybranego zlecenia
     */
    private void updateStatus() {
        updateButton.setOnAction((event) -> {
            if (validateInputs()) {
                Zlecenie zlecenie = updateZlecenieFromInput();
                boolean isSaved = new ZlecenieDao().updateZlecenie(zlecenie);

                if (isSaved) {
                    UpdateStatus.setIsStatusUpdated(true);
                    delayWindowClose(event);
                    AlertPopUp.successAlert("Status zmieniony!");
                }
            }
        });
    }

    /**
     * Metoda walidująca pola zmiany statusu
     * @return zwraca true jeżeli walidacja przeszła pomyślnie
     */
    private boolean validateInputs() {
        /** Walidacja pola status */
        if (nowyStatusComboBox.getValue() == null) {
            errorText.setText("Pole status nie może być puste!");
            return false;
        }

        /** Walidacja pola id */
        if (idZlecnieComboBox.getValue() == null) {
            errorText.setText("Pole id nie może być puste!");
            return false;
        }
        return true;
    }

    /**
     * Metoda tworząca obiekt zlecenie na podstawie pobranych pól z widoku "updateStatus.fxml"
     * @return zwraca obiekt Zlecenie
     */
    private Zlecenie updateZlecenieFromInput() {
        Zlecenie zlecenie = idZlecnieComboBox.getValue();
        zlecenie.setStatus(nowyStatusComboBox.getValue());
        return zlecenie;
    }


    /**
     * Metoda zamykająca okno z opóźnieniem po dodaniu zlecenia
     * @param event
     */
    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    /**
     * Metoda zamykająca okno "updateStatus.fxml"
     * @param event
     */
    @FXML
    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
