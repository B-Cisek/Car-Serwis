package car.serwis.controller;

import car.serwis.database.dao.KategoriaDao;
import car.serwis.database.model.Kategoria;
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

public class AddKategoriaController implements Initializable {
    @FXML
    private Button addKategoriaButton;

    @FXML
    private Button anulujButton;

    @FXML
    private Text errorText;

    @FXML
    private TextField nazwaTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anulujButton.setOnAction(SceneController::close);
        saveNewKategoria();
    }

    /**
     * Metoda nasluchujaca button i dodająca kategorie od bazy
     */
    private void saveNewKategoria() {
        addKategoriaButton.setOnAction((event) -> {
            if (validateInputs()) {
                Kategoria kategoria = createKategoriaFromInput();
                boolean isSaved = new KategoriaDao().createKategoria(kategoria);

                if (isSaved) {
                    UpdateStatus.setIsKategoriaAdded(true);
                    delayWindowClose(event);
                    AlertPopUp.successAlert("Kategoira dodana!");
                }
            }
        });
    }

    /**
     * Metoda walidująca pola kategoria
     * @return zwraca true jeżeli walidacja przeszła pomyślnie
     */
    private boolean validateInputs() {
        /** Walidacja pola nazwa */
        if (ValidatorFields.isBlank(nazwaTextField.getText())) {
            errorText.setText("Pole nazwa nie może być puste!");
            return false;
        }
        return true;
    }


    /**
     * Metoda tworząca obiekt kategoria na podstawie pobranych pól z widoku "addKategoria.fxml"
     * @return zwraca obiekt Kategoria
     */
    private Kategoria createKategoriaFromInput() {
        Kategoria kategoria = new Kategoria();
        kategoria.setNazwaKategori(nazwaTextField.getText());
        return kategoria;
    }


    /**
     * Metoda zamykająca okno z opóźnieniem po dodaniu kategori
     */
    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }


    /**
     * Metoda zamykająca okno "addKategoria.fxml"
     */
    @FXML
    private void closeWindow(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
