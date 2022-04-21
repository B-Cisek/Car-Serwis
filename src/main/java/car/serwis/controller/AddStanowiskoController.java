package car.serwis.controller;

import car.serwis.database.dao.StanowiskoDao;
import car.serwis.database.model.Stanowisko;
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

public class AddStanowiskoController implements Initializable {

    @FXML
    private Button addStanowiskoButton;

    @FXML
    private Text errorText;

    @FXML
    private TextField nazwaTextField;

    @FXML
    private Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveNewStanowisko();
    }


    @FXML
    private void saveNewStanowisko() {
        addStanowiskoButton.setOnAction((event) -> {
            if (validateInputs()) {
                Stanowisko stanowisko = createStanowiskoFromInput();
                boolean isSaved = new StanowiskoDao().createStanowisko(stanowisko);

                if (isSaved) {
                    errorText.setText("Stanowisko dodane!");
                    delayWindowClose(event);
                }
            }
        });
    }

    private boolean validateInputs() {
        if (nazwaTextField.getText().equals("")) {
            errorText.setText("*Pole nazwa nie może być puste!");
            return false;
        }

        return true;
    }

    private Stanowisko createStanowiskoFromInput() {
        Stanowisko stanowisko = new Stanowisko();

        stanowisko.setNazwa(nazwaTextField.getText());

        return stanowisko;
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

}
