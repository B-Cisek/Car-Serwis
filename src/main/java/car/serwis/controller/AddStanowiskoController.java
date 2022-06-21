package car.serwis.controller;

import car.serwis.database.dao.StanowiskoDao;
import car.serwis.database.model.Stanowisko;
import car.serwis.helpers.AlertPopUp;
import car.serwis.helpers.UpdateStatus;
import car.serwis.helpers.UsersPermissions;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Kontroler widoku "addStanowisko.fxml"
 */
public class AddStanowiskoController implements Initializable {

    /** Button wywołujący metode saveNewStanowisko */
    @FXML
    private Button addStanowiskoButton;

    /** Text wyświetlający błędy */
    @FXML
    private Text errorText;

    /** TextField nazwa stanowiska */
    @FXML
    private TextField nazwaTextField;

    /** Button zamykający okno */
    @FXML
    private Button anulujButton;

    /** ComboBox z uprawnieniami użytkownika */
    @FXML
    private ComboBox<UsersPermissions> uprawnieniaComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anulujButton.setOnAction(SceneController::close);
        saveNewStanowisko();
        uprawnieniaComboBox.setItems(getUsersPermissionsObservableList());
    }

    /**
     * Metoda nasluchujaca button i dodająca stanowisko
     */
    @FXML
    private void saveNewStanowisko() {
        addStanowiskoButton.setOnAction((event) -> {
            if (validateInputs()) {
                Stanowisko stanowisko = createStanowiskoFromInput();
                boolean isSaved = new StanowiskoDao().saveStanowisko(stanowisko);
                if (isSaved) {
                    UpdateStatus.setIsStanowiskoAdded(true);
                    delayWindowClose(event);
                    AlertPopUp.successAlert("Stanowisko dodane!");
                }
            }
        });
    }

    /**
     * Metoda walidująca pola stanowiska
     * @return zwraca true jeżeli walidacja przeszła pomyślnie
     */
    private boolean validateInputs() {
        if (nazwaTextField.getText().isBlank()) {
            errorText.setText("Pole nazwa nie może być puste!");
            return false;
        }
        if (uprawnieniaComboBox.getValue() == null){
            errorText.setText("Pole uprawnienia nie może być puste!");
            return false;
        }
        return true;
    }

    /**
     * Metoda tworząca obiekt stanowisko na podstawie pobranych pól z widoku "addStanowisko.fxml"
     * @return zwraca obiekt Stanowisko
     */
    private Stanowisko createStanowiskoFromInput() {
        Stanowisko stanowisko = new Stanowisko();
        stanowisko.setNazwaStanowiska(nazwaTextField.getText());
        stanowisko.setUprawnienia(uprawnieniaComboBox.getValue());
        return stanowisko;
    }

    /**
     * Metoda towrząca ObservableList UsersPermissions
     * @return zwraca ObservableList UsersPermissions
     */
    private ObservableList<UsersPermissions> getUsersPermissionsObservableList() {
        ObservableList<UsersPermissions> list = FXCollections.observableArrayList();
        list.addAll(
                UsersPermissions.ADMIN,
                UsersPermissions.MAGAZYNIER,
                UsersPermissions.MECHANIK,
                UsersPermissions.OBSLUGA_KLIENTA);
        return list;
    }

    /**
     * Metoda zamykająca okno z opóźnieniem po dodaniu stanowiska
     */
    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    /**
     * Metoda zamykająca okno "addStanowisko.fxml"
     */
    @FXML
    private void closeWindow(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
