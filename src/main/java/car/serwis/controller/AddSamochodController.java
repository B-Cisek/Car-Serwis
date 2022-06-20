package car.serwis.controller;

import car.serwis.database.dao.SamochodDao;
import car.serwis.database.model.Samochod;
import car.serwis.helpers.AlertPopUp;
import car.serwis.helpers.UpdateStatus;
import car.serwis.helpers.ValidatorFields;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Kontroler widoku "addSamochód.fxml"
 */
public class AddSamochodController implements Initializable {

    @FXML
    private Button addSamochodButton;

    @FXML
    private Button anulujButton;

    @FXML
    private TextField markaTextField;

    @FXML
    private TextField modelTextField;

    @FXML
    private Text errorText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anulujButton.setOnAction(SceneController::close);
        saveNewSamochod();
    }

    /**
     * Metoda zapisująca nowy samochód do bazy
     */
    private void saveNewSamochod() {
        addSamochodButton.setOnAction((event) -> {
            if (validateSamochodInputs()) {
                Samochod samochod = createSamochodFromInput();

                boolean isSaved = new SamochodDao().createSamochod(samochod);

                if (isSaved) {
                    UpdateStatus.setIsSamochodAdded(true);
                    delayWindowClose(event);
                    AlertPopUp.successAlert("Samochód dodany!");
                }
            }
        });
    }

    /**
     * Metoda walidująca pola samochód
     * @return zwraca true jeżeli walidacja przeszła pomyślnie
     */
    private boolean validateSamochodInputs() {
        /** Walidacja pola marka */
        if (ValidatorFields.isBlank(markaTextField.getText())) {
            errorText.setText("Pole marka nie może być puste!");
            return false;
        }

        /** Walidacja pola model */
        if (ValidatorFields.isBlank(modelTextField.getText())) {
            errorText.setText("Pole model nie może być puste!");
            return false;
        }
        return true;
    }


    /**
     * Metoda tworząca obiekt samochód na podstawie pobranych pól z widoku "addSamochod.fxml"
     * @return zwraca obiekt Samochod
     */
    private Samochod createSamochodFromInput() {
        Samochod samochod = new Samochod();
        samochod.setMarka(markaTextField.getText());
        samochod.setModel(modelTextField.getText());
        return samochod;
    }


    /**
     * Metoda zamykająca okno z opóźnieniem po dodaniu samchodu
     * @param event
     */
    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    /**
     * Metoda zamykająca okno "addSamochod.fxml"
     * @param event
     */
    @FXML
    private void closeWindow(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }



}
