package car.serwis.controller;

import car.serwis.database.dao.PracownikDao;
import car.serwis.database.dao.StanowiskoDao;
import car.serwis.database.model.Pracownik;
import car.serwis.database.model.Stanowisko;
import car.serwis.helpers.UpdateStatus;
import car.serwis.helpers.WindowManagement;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPracownikController implements Initializable {


    @FXML
    private TextField hasloTextField;

    @FXML
    private TextField imieTextField;

    @FXML
    private TextField loginTextField;

    @FXML
    private Text errorText;

    @FXML
    private TextField nazwiskoTextField;

    @FXML
    private DatePicker pracujeOdDatePicker;

    @FXML
    private ComboBox<Stanowisko> stanowiskaComboBox;

    @FXML
    private Button anulujButton;

    @FXML
    private AnchorPane addPracownikAnchorePane;

    PracownikDao pracownikDao = new PracownikDao();
    WindowManagement windowManagement = new WindowManagement();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stanowiskaComboBox.setItems(getStanowiskaObservableList());
        windowManagement.initializeExitButtonAnchorPane(anulujButton,addPracownikAnchorePane);
    }

    private ObservableList<Stanowisko> getStanowiskaObservableList() {
        ObservableList<Stanowisko> list = FXCollections.observableArrayList();
        list.addAll(new StanowiskoDao().getStanowiska());
        return list;
    }

    private Pracownik createPracownikFromInput() {
        Pracownik pracownik = new Pracownik();
        pracownik.setImie(imieTextField.getText());
        pracownik.setNazwisko(nazwiskoTextField.getText());
        pracownik.setLogin(loginTextField.getText());
        pracownik.setHaslo(hasloTextField.getText());
        pracownik.setPracujeOd(pracujeOdDatePicker.getValue());
        pracownik.setStanowisko(stanowiskaComboBox.getValue());

        return pracownik;
    }


    private boolean validateInputs() {
        if (stanowiskaComboBox.getValue() == null) {
            errorText.setText("*Pole stanowisko nie może być puste!");
            return false;
        }

        if(pracujeOdDatePicker.getValue() == null) {
            errorText.setText("*Pole data nie może być puste!");
            return false;
        }


        if (imieTextField.getText().isBlank()) {
            errorText.setText("*Pole imie nie może być puste!");
            return false;
        }

        if (nazwiskoTextField.getText().isBlank()) {
            errorText.setText("*Pole nazwisko nie może być puste!");
            return false;
        }

        if (loginTextField.getText().isBlank()){
            errorText.setText("*Pole login nie może być puste!");
            return false;
        }

        if (hasloTextField.getText().isBlank()) {
            errorText.setText("*Pole hasło nie może być puste!");
            return false;
        }
        return true;
    }

    @FXML
    private void createPracownik(ActionEvent event) {
        if(validateInputs()) {
            Pracownik pracownik = createPracownikFromInput();
            boolean isSaved = new PracownikDao().createPracownik(pracownik);
            if (isSaved) {
                UpdateStatus.setIsPracownikAdded(true);
                errorText.setText("Dodano pracownika!");
                errorText.setStyle("-fx-text-fill: #2CC97E; -fx-font-size: 15px;");
                delayWindowClose(event);
            }
        }
    }


    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) anulujButton.getScene().getWindow();
        stage.close();
    }
}
