package car.serwis.controller;

import car.serwis.database.dao.SamochodDao;
import car.serwis.database.dao.StanowiskoDao;
import car.serwis.database.model.Samochod;
import car.serwis.database.model.Stanowisko;
import car.serwis.helpers.UpdateStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

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
    private AnchorPane addSamochodAnchorePane;

    @FXML
    private Text errorText;


    private void saveNewSamochod() {
        addSamochodButton.setOnAction((event) -> {
            if (validateInputs()) {
                Samochod samochod = createSamochodFromInput();

                boolean isSaved = new SamochodDao().createSamochod(samochod);

                if (isSaved) {
                    UpdateStatus.setIsSamochodAdded(true);
                    errorText.setText("Samochód dodany!");
                    errorText.setStyle("-fx-text-fill: #2CC97E; -fx-font-size: 15px;");
                    delayWindowClose(event);
                }
            }
        });
    }

    private boolean validateInputs() {
        if (markaTextField.getText().equals("")) {
            errorText.setText("*Pole nazwa nie może być puste!");
            return false;
        }

        if (modelTextField.getText().equals("")) {
            errorText.setText("*Pole nazwa nie może być puste!");
            return false;
        }

        return true;
    }

    private Samochod createSamochodFromInput() {
        Samochod samochod = new Samochod();

        samochod.setMarka(markaTextField.getText());
        samochod.setModel(modelTextField.getText());

        return samochod;
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


    private void initializeExitButton(){
        anulujButton.setOnAction((x) -> {
            getStage().close();
        });
    }

    private Stage getStage(){
        return (Stage) addSamochodAnchorePane.getScene().getWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeExitButton();
        saveNewSamochod();

    }
}