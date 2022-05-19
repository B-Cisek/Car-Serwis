package car.serwis.controller;

import car.serwis.database.dao.KontrahentDao;
import car.serwis.database.model.Kontrahent;
import car.serwis.helpers.UpdateStatus;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AddKontrahentController implements Initializable {

    @FXML
    private AnchorPane addKontrahentAnchorePane;

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
        initializeExitButton();

    }

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

    private boolean validateInputs() {

        if (imieTextField.getText().equals("")) {
            errorText.setText("*Pole imie nie może być puste!");
            return false;
        }

        if(nazwiskoTextField.getText().equals("")) {
            errorText.setText("*Pole nazwisko nie może być puste!");
            return false;
        }


        if (telefonTextField.getText().equals("")) {
            errorText.setText("*Pole telefon nie może być puste!");
            return false;
        }

        if (peselTextField.getText().equals("")) {
            errorText.setText("*Pole pesel nie może być puste!");
            return false;
        }

        if (nipTextField.getText().equals("")){
            errorText.setText("*Pole nip nie może być puste!");
            return false;
        }

        if (kodPocztowyTextField.getText().equals("")) {
            errorText.setText("*Pole kod pocztowy nie może być puste!");
            return false;
        }

        if (nazwaFirmyTextField.getText().equals("")) {
            errorText.setText("*Pole nazwa firmy nie może być puste!");
            return false;
        }

        if (miejscowoscTextField.getText().equals("")) {
            errorText.setText("*Pole miejscowość nie może być puste!");
            return false;
        }

        if (ulicaTextField.getText().equals("")) {
            errorText.setText("*Pole ulica nie może być puste!");
            return false;
        }

        return true;
    }

    @FXML
    private void createKontrahent(ActionEvent event) {
        if(validateInputs()) {
            Kontrahent kontrahent = createKontrahentFromInput();
            boolean isSaved = new KontrahentDao().createKontrahent(kontrahent);
            if (isSaved) {
                UpdateStatus.setIsKontrahentAdded(true);
                errorText.setText("Dodano kontrahenta!");
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


    private void initializeExitButton(){
        anulujButton.setOnAction((x) -> {
            getStage().close();
        });
    }

    private Stage getStage(){
        return (Stage) addKontrahentAnchorePane.getScene().getWindow();
    }


}

