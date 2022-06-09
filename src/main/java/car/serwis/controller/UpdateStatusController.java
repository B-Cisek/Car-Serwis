package car.serwis.controller;

import car.serwis.database.dao.ZlecenieDao;
import car.serwis.database.model.Zlecenie;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

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
    private AnchorPane updateStatusAnchorePane;

    @FXML
    private Text errorText;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeExitButton();
        updateStatus();
        idZlecnieComboBox.setItems(getZlecenieObservableList());
        nowyStatusComboBox.setItems(getStatusObservableList());
    }


    private ObservableList<ZlecenieStatus> getStatusObservableList() {
        ObservableList<ZlecenieStatus> list = FXCollections.observableArrayList();
        list.addAll(ZlecenieStatus.NOWE);
        list.addAll(ZlecenieStatus.OCZEKUJACE);
        list.addAll(ZlecenieStatus.W_TRAKCIE);
        list.addAll(ZlecenieStatus.GOTOWE);
        return list;
    }


    private ObservableList<Zlecenie> getZlecenieObservableList() {
        ObservableList<Zlecenie> list = FXCollections.observableArrayList();
        list.addAll(new ZlecenieDao().getMechanikZlecenia());
        return list;
    }

    private void updateStatus() {
        updateButton.setOnAction((event) -> {
            if (validateInputs()) {
                Zlecenie zlecenie = updateZlecenieFromInput();
                boolean isSaved = new ZlecenieDao().updateZlecenie(zlecenie);

                if (isSaved) {
                    UpdateStatus.setIsStatusUpdated(true);
                    errorText.setText("Ztatus zmieniony!");
                    errorText.setStyle("-fx-text-fill: #2CC97E; -fx-font-size: 15px;");
                    delayWindowClose(event);
                }
            }
        });
    }

    private boolean validateInputs() {
        if (nowyStatusComboBox.getValue() == null) {
            errorText.setText("*Pole status nie może być puste!");
            return false;
        }

        if (idZlecnieComboBox.getValue() == null) {
            errorText.setText("*Pole id nie może być puste!");
            return false;
        }

        return true;
    }

    private Zlecenie updateZlecenieFromInput() {
        Zlecenie zlecenie = new Zlecenie();
        zlecenie = idZlecnieComboBox.getValue();
        zlecenie.setStatus(nowyStatusComboBox.getValue());
        return zlecenie;
    }


    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


    private void initializeExitButton() {
        anulujButton.setOnAction((x) -> {
            getStage().close();
        });
    }

    private Stage getStage() {
        return (Stage) updateStatusAnchorePane.getScene().getWindow();
    }
}
