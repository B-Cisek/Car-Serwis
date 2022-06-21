package car.serwis.controller;

import car.serwis.database.dao.JednostkaDao;
import car.serwis.database.model.Jednostka;
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
 * Kontroler widoku "addJednostka.fxml"
 * klasa odpowiedzialna za dodanie jednostki do bazy
 */
public class AddJednostkaController implements Initializable {
    @FXML
    private Button addJednostkaButton;

    @FXML
    private Button anulujButton;

    @FXML
    private Text errorText;

    @FXML
    private TextField nazwaJednostkiTextField;

    @FXML
    private TextField skrotTextField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anulujButton.setOnAction(SceneController::close);
        saveNewJednostka();
    }

    /**
     * Metoda nasluchujaca button i dodająca jednstke od bazy
     */
    private void saveNewJednostka() {
        addJednostkaButton.setOnAction((event) -> {
            if (validateInputs()) {
                Jednostka jednostka = createJednostkaFromInput();
                boolean isSaved = new JednostkaDao().createJednostka(jednostka);

                if (isSaved) {
                    UpdateStatus.setIsJednostkaAdded(true);
                    delayWindowClose(event);
                    AlertPopUp.successAlert("Jednostka dodana!");
                }
            }
        });
    }

    /**
     * Metoda walidująca pola jednostki
     *
     * @return zwraca true jeżeli walidacja przeszła pomyślnie
     */
    private boolean validateInputs() {
        /** Walidacja pola nazwa jednostki */
        if (ValidatorFields.isBlank(nazwaJednostkiTextField.getText())) {
            errorText.setText("Pole nazwa nie może być puste!");
            return false;
        }

        /** Walidacja pola skrót */
        if (ValidatorFields.isBlank(skrotTextField.getText())) {
            errorText.setText("Pole skrót nie może być puste!");
            return false;
        }

        return true;
    }

    /**
     * Metoda tworząca obiekt jednostka na podstawie pobranych pól z widoku "addJednostka.fxml"
     *
     * @return zwraca obiekt Jednostka
     */
    private Jednostka createJednostkaFromInput() {
        Jednostka jednostka = new Jednostka();
        jednostka.setNazwaJednostki(nazwaJednostkiTextField.getText());
        jednostka.setSkrot(skrotTextField.getText());
        return jednostka;
    }


    /**
     * Metoda zamykająca okno z opóźnieniem po dodaniu jednostki
     *
     * @param event
     */
    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    /**
     * Metoda zamykająca okno "addJednostka.fxml"
     *
     * @param event
     */
    @FXML
    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
