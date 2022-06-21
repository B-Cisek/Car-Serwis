package car.serwis.controller;

import car.serwis.database.dao.PracownikDao;
import car.serwis.database.dao.StanowiskoDao;
import car.serwis.database.model.Pracownik;
import car.serwis.database.model.Stanowisko;
import car.serwis.helpers.AlertPopUp;
import car.serwis.helpers.UpdateStatus;
import car.serwis.helpers.ValidatorFields;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Kontroler widoku "addPracownik.fxml"
 */
public class AddPracownikController implements Initializable {

    /** TextField hasło */
    @FXML
    private TextField hasloTextField;

    /** TextField imie */
    @FXML
    private TextField imieTextField;

    /** TextField login */
    @FXML
    private TextField loginTextField;

    /** Text wyświetlający błędy */
    @FXML
    private Text errorText;

    /** TextField nazwisko */
    @FXML
    private TextField nazwiskoTextField;

    /** DatePicker data rozpoczęcia pracy */
    @FXML
    private DatePicker pracujeOdDatePicker;

    /** ComboBox dostępnych stanowisk */
    @FXML
    private ComboBox<Stanowisko> stanowiskaComboBox;

    /** Button zamykający okno */
    @FXML
    private Button anulujButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stanowiskaComboBox.setItems(getStanowiskaObservableList());
        anulujButton.setOnAction(SceneController::close);
    }

    /**
     * Metoda pobierająca pracowników z bazy i dodająca ich ObservableList
     * @return zwraca ObservableList stanowisk z bazy
     */
    private ObservableList<Stanowisko> getStanowiskaObservableList() {
        ObservableList<Stanowisko> list = FXCollections.observableArrayList();
        list.addAll(new StanowiskoDao().getStanowiska());
        return list;
    }

    /**
     * Metoda haszujaca hasło
     * @param haslo hasło w formie plaintext
     * @return zwraca zahaszowane hasło
     */
    private String hashPassword(String haslo){
        return BCrypt.hashpw(haslo, BCrypt.gensalt(11));
    }


    /**
     * Metoda tworząca obiekt pracownika na podstawie pobranych pól z widoku "addPracownik.fxml"
     * @return zwraca obiekt Pracownik
     */
    private Pracownik createPracownikFromInput(){
        Pracownik pracownik = new Pracownik();
        System.out.println(hashPassword(hasloTextField.getText()));
        pracownik.setImie(imieTextField.getText());
        pracownik.setNazwisko(nazwiskoTextField.getText());
        pracownik.setLogin(loginTextField.getText());
        pracownik.setHaslo(hashPassword(hasloTextField.getText()));
        pracownik.setPracujeOd(pracujeOdDatePicker.getValue());
        pracownik.setStanowisko(stanowiskaComboBox.getValue());

        return pracownik;
    }

    /**
     * Metoda walidująca pola pracownika
     * @return zwraca true jeżeli walidacja przeszła pomyślnie
     */
    private boolean validateInputs() {
        /** Walidacja pola stanowisko */
        if (stanowiskaComboBox.getValue() == null) {
            errorText.setText("Pole stanowisko nie może być puste!");
            return false;
        }

        /** Walidacja pola data */
        if(pracujeOdDatePicker.getValue() == null) {
            errorText.setText("Pole data nie może być puste!");
            return false;
        }


        /** Walidacja pola imie */
        if (imieTextField.getText().isBlank()) {
            errorText.setText("Pole imie nie może być puste!");
            return false;
        }

        /** Walidacja pola nazwisko */
        if (nazwiskoTextField.getText().isBlank()) {
            errorText.setText("Pole nazwisko nie może być puste!");
            return false;
        }

        /** Walidacja pola login */
        if (loginTextField.getText().isBlank()){
            errorText.setText("Pole login nie może być puste!");
            return false;
        }


        if (!(new PracownikDao().getConnectedPracownikLogin(loginTextField.getText()) == null)){
            errorText.setText("Ten login jest już w bazie!");
            return false;
        }


        /** Walidacja pola hasło */
        if (hasloTextField.getText().isBlank()) {
            errorText.setText("Pole hasło nie może być puste!");
            return false;
        }

        if (!ValidatorFields.isCorrectPassword(hasloTextField.getText())){
            errorText.setText("Hasło powinno być dłuższe niż 4 znaki, nie powinno zawierać spacji!");
            return false;
        }
        return true;
    }

    /**
     * Metoda nasluchujaca button i dodająca pracownika
     */
    @FXML
    private void createPracownik(ActionEvent event) {
        if(validateInputs()) {
            Pracownik pracownik = createPracownikFromInput();
                boolean isSaved = new PracownikDao().createPracownik(pracownik);
            if (isSaved) {
                UpdateStatus.setIsPracownikAdded(true);
                delayWindowClose(event);
                AlertPopUp.successAlert("Pracownik dodany!");
            }
        }
    }

    /**
     * Metoda zamykająca okno z opóźnieniem po dodaniu pracownika
     */
    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    /**
     * Metoda zamykająca okno "addPracownik.fxml"
     */
    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) anulujButton.getScene().getWindow();
        stage.close();
    }
}
