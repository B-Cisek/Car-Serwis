package car.serwis.controller;

import car.serwis.database.dao.KontrahentDao;
import car.serwis.database.model.Kontrahent;
import car.serwis.helpers.AlertPopUp;
import car.serwis.helpers.UpdateStatus;
import car.serwis.helpers.ValidatorFields;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Kontroler widoku "addKontrahent.fxml"
 * klasa odpowiedzialna za dodanie kontrahenta do bazy
 */
public class AddKontrahentController implements Initializable {
    @FXML
    private Button anulujButton;

    @FXML
    private Text errorText;

    @FXML
    private TextField imieTextField;

    @FXML
    private TextField kodPocztowyTextField;

    @FXML
    private TextField miejscowoscTextField;

    @FXML
    private TextField nazwaFirmyTextField;

    @FXML
    private TextField nazwiskoTextField;

    @FXML
    private TextField nipTextField;

    @FXML
    private TextField peselTextField;

    @FXML
    private TextField telefonTextField;

    @FXML
    private TextField ulicaTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anulujButton.setOnAction(SceneController::close);
    }

    /**
     * Metoda tworząca obiekt kontrahenta na podstawie pobranych pól z widoku "addKontrahent.fxml"
     * @return zwraca obiekt Kontrahent
     */
    private Kontrahent createKontrahentFromInput() {
        Kontrahent kontrahent = new Kontrahent();
        kontrahent.setImie(imieTextField.getText());
        kontrahent.setNazwisko(nazwiskoTextField.getText());
        kontrahent.setTelefon(telefonTextField.getText());
        kontrahent.setPesel(peselTextField.getText());
        kontrahent.setNip(nipTextField.getText());
        kontrahent.setKodPocztowy(kodPocztowyTextField.getText());
        kontrahent.setNazwaFirmy(nazwaFirmyTextField.getText());
        kontrahent.setMiejscowosc(miejscowoscTextField.getText());
        kontrahent.setUlica(ulicaTextField.getText());

        return kontrahent;
    }

    /**
     * Metoda walidująca pola kontrahent
     * @return zwraca true jeżeli walidacja przeszła pomyślnie
     */
    private boolean validatekKontrahentInputs() {
        /** Walidacja pola imie */
        if (ValidatorFields.isBlank(imieTextField.getText())){
            errorText.setText("Pole imie nie może być puste!");
            return false;
        }else if (!ValidatorFields.isText(imieTextField.getText())){
            errorText.setText("Nieprawidłowa wartość pola imie!");
            return false;
        }

        /** Walidacja pola nazwisko */
        if (ValidatorFields.isBlank(nazwiskoTextField.getText())){
            errorText.setText("Pole nazwisko nie może być puste!");
            return false;
        }else if (!ValidatorFields.isText(nazwiskoTextField.getText())){
            errorText.setText("Nieprawidłowa wartość pola nazwisko!");
            return false;
        }

        /** Walidacja pola telefon */
        if (ValidatorFields.isBlank(telefonTextField.getText())){
            errorText.setText("Pole telefon nie może być puste!");
            return false;
        }else if (!ValidatorFields.isNumeric(telefonTextField.getText())){
            errorText.setText("Nieprawidłowa wartość pola telefon!");
            return false;
        }

        /** Walidacja pola miejscowośc */
        if (ValidatorFields.isBlank(miejscowoscTextField.getText())){
            errorText.setText("Pole miejscowość nie może być puste!");
            return false;
        }else if (!ValidatorFields.isText(miejscowoscTextField.getText())){
            errorText.setText("Nieprawidłowa wartość pola miejscowość!");
            return false;
        }

        /** Walidacja pola nazwa firmy */
        if (ValidatorFields.isBlank(nazwaFirmyTextField.getText())){
            errorText.setText("Pole nazwa firmy nie może być puste!");
            return false;
        }

        /** Walidacja pola NIP */
        if (ValidatorFields.isBlank(nipTextField.getText())){
            errorText.setText("Pole NIP nie może być puste!");
            return false;
        }else if (!ValidatorFields.isNip(nipTextField.getText())){
            errorText.setText("Nieprawidłowa wartość pola NIP!");
            return false;
        }

        /** Walidacja pola PESEL */
        if (ValidatorFields.isBlank(peselTextField.getText())){
            errorText.setText("Pole PESEL nie może być puste!");
            return false;
        }else if (!ValidatorFields.isPesel(peselTextField.getText())){
            errorText.setText("Nieprawidłowa wartość pola PESEL!");
            return false;
        }

        /** Walidacja pola kod pocztowy */
        if (ValidatorFields.isBlank(kodPocztowyTextField.getText())){
            errorText.setText("Pole kod pocztowy nie może być puste!");
            return false;
        }else if (!ValidatorFields.isKodPocztowy(kodPocztowyTextField.getText())){
            errorText.setText("Nieprawidłowa wartość pola kod pocztowy!");
            return false;
        }

        /** Walidacja pola ulica */
        if (ValidatorFields.isBlank(ulicaTextField.getText())){
            errorText.setText("Pole ulica nie może być puste!");
            return false;
        }
        return true;
    }

    /**
     * Metoda nasluchujaca button i dodająca kontrahenta do bazy
     * @param event
     */
    @FXML
    private void createKontrahent(ActionEvent event) {
        if(validatekKontrahentInputs()) {
            Kontrahent kontrahent = createKontrahentFromInput();
            boolean isSaved = new KontrahentDao().createKontrahent(kontrahent);
            if (isSaved) {
                UpdateStatus.setIsKontrahentAdded(true);
                delayWindowClose(event);
                AlertPopUp.successAlert("Kontrahent dodany!");
            }
        }
    }

    /**
     * Metoda zamykająca okno z opóźnieniem po dodaniu kontrahenta
     * @param event
     */
    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    /**
     * Metoda zamykająca okno "addKontrahent.fxml"
     * @param event
     */
    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) anulujButton.getScene().getWindow();
        stage.close();
    }
}

