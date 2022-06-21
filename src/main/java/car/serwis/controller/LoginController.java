package car.serwis.controller;

import car.serwis.database.dao.PracownikDao;
import car.serwis.database.model.Pracownik;
import car.serwis.helpers.AlertPopUp;
import car.serwis.helpers.CurrentPracownik;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Kontroler widoku "login.fxml", klasa obsługuje logowanie do systemu
 */
public class LoginController implements Initializable {
    /** Przycisk zamykający okno logowania */
    @FXML
    private Button exitButton;

    /** Pole formularza - hasło */
    @FXML
    private PasswordField hasloTextField;

    /** Pole formularza - login */
    @FXML
    private TextField loginTextField;

    /** Text wyświtlający błedy */
    @FXML
    private Text infoLine;

    /** Inicjalizacja obiektu Pracownik Data Access Object */
    PracownikDao pracownikDao = new PracownikDao();

    /** Inicjalizacja obiektu AlertPopUp */
    AlertPopUp alertPopUp = new AlertPopUp();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setOnAction(SceneController::close);
    }

    /**
     * Metoda sprawdzająca poprawność daych użytkownika i logująca go do systemu
     */
    @FXML
    private void loginPracownik(ActionEvent event) {
        String login = loginTextField.getText();
        String haslo = hasloTextField.getText();

        Pracownik pracownik = pracownikDao.getConnectedPracownikLogin(login);

        if (validFields()) {
            if (pracownik == null || !login.equals(pracownik.getLogin())) {
                infoLine.setText("Nie ma takiego loginu w bazie!");
            } else {
                if (BCrypt.checkpw(haslo, pracownik.getHaslo())) {
                    CurrentPracownik.setCurrentPracownik(pracownik);
                    infoLine.setStyle("-fx-fill: #2CC97E");
                    infoLine.setText("Witaj, " + CurrentPracownik.getCurrentPracownik().getLogin() + "!");
                    Stage waitnigPopUp = alertPopUp.waitingPopUp("Łączenie z serwerem..");
                    waitnigPopUp.show();
                    PauseTransition delay = new PauseTransition(Duration.seconds(2));
                    delay.setOnFinished(event2 -> {
                        try {
                            SceneController.getPulpitScene(event);
                            waitnigPopUp.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    delay.play();
                } else {
                    infoLine.setText("Błędne hasło!");
                }
            }
        }
    }


    /**
     * Metoda walidująca login i hasło
     * @return zwraca true jeżeli pola nie są puste
     */
    public boolean validFields() {
        if (loginTextField.getText().isBlank()) {
            infoLine.setText("Pole login nie może być puste!");
            return false;
        }

        if (hasloTextField.getText().isBlank()) {
            infoLine.setText("Pole hasło nie może być puste!");
            return false;
        }
        return true;
    }

}

