package car.serwis.controller;

import car.serwis.database.dao.PracownikDao;
import car.serwis.database.model.Pracownik;
import car.serwis.helpers.CurrentPracownik;
import car.serwis.helpers.ScenePath;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private static final String APP_TITLE = "Car Serwis";


    @FXML
    private Button exitButton;

    @FXML
    private PasswordField hasloTextField;

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginTextField;

    @FXML
    private Text infoLine;


    PracownikDao pracownikDao = new PracownikDao();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        close();
    }


    private void checkPass(String plainPassword, String hashedPassword) {
        if (BCrypt.checkpw(plainPassword, hashedPassword))
            System.out.println("The password matches.");
        else
            System.out.println("The password does not match.");
    }


    @FXML
    private void loginPracownik(ActionEvent event) {
        String login = loginTextField.getText();
        String haslo = hasloTextField.getText();
        Pracownik pracownik = pracownikDao.getConnectedPracownikLogin(login);

        if (validFields()) {
            if (pracownik == null) {
                infoLine.setText("Nie ma takiego loginu w bazie!");
            } else {
                if (BCrypt.checkpw(haslo, pracownik.getHaslo())) {
                    CurrentPracownik.setCurrentPracownik(pracownik);
                    infoLine.setText("Witaj, " + CurrentPracownik.getCurrentPracownik().getLogin() + "!");
                    PauseTransition delay = new PauseTransition(Duration.seconds(2));
                    delay.setOnFinished(event2 -> {
                        try {
                            SceneController.getPulpitScene(event);

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


    private void close() {
        exitButton.setOnAction(SceneController::close);
    }


}

