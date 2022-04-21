package car.serwis.controller;

import car.serwis.database.dao.PracownikDao;
import car.serwis.database.dao.StanowiskoDao;
import car.serwis.database.model.Pracownik;
import car.serwis.database.model.Stanowisko;
import car.serwis.helpers.UpdateStatus;
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stanowiskaComboBox.setItems(getStanowiskaObservableList());
        initializeExitButton();
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

        if (pracujeOdDatePicker.getValue() == null) {
            errorText.setText("*Date must be selected from calendar!");
            return false;
        }

        if(pracujeOdDatePicker.getValue() == null) {
            errorText.setText("*You must select pet!");
            return false;
        }


        if (imieTextField.getText().equals("")) {
            errorText.setText("*You must add visit description!");
            return false;
        }

        if (nazwiskoTextField.getText().equals("")) {
            errorText.setText("*You must add visit description!");
            return false;
        }

        if (loginTextField.getText().equals("")) {
            errorText.setText("*You must add visit description!");
            return false;
        }

        if (hasloTextField.getText().equals("")) {
            errorText.setText("*You must add visit description!");
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
                errorText.setText("Visit is added!");
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



    private void initializeExitButton(){
        anulujButton.setOnAction((x) -> {
            getStage().close();
        });
    }

    private Stage getStage(){
        return (Stage) addPracownikAnchorePane.getScene().getWindow();
    }


}
