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
        //initializeExitButton();
        close();
        //initializeLoginButton();
    }



    @FXML
    private void loginPracownik(ActionEvent event) throws IOException, InterruptedException {
        String login = loginTextField.getText();
        String haslo = hasloTextField.getText();


        if(!validFields()) {
            infoLine.setText("Login i hasło nie może być puste!");
            return;
        }

        if (!validateLogin()) {
            infoLine.setText("Błędne dane!");
            return;
        }

        infoLine.setText("Welcome, " + CurrentPracownik.getCurrentPracownik().getLogin() + "!");

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished( event2 -> {
            try {
                SceneController.getPulpitScene(event);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delay.play();
    }

    boolean validFields() {
        return !loginTextField.getText().isBlank() && !hasloTextField.getText().isBlank();
    }

    private boolean validateLogin() {
        // TODO walidacja logowania
        Pracownik pracownik = pracownikDao.getConnectedPracownik(loginTextField.getText(), hasloTextField.getText());
        if (pracownik.getLogin().equals(loginTextField.getText())){
            System.out.println("te same");
        }else {
            System.out.println("nie");
        }
        System.out.println(pracownik.getLogin() + " || " + pracownik.getHaslo());
        if (pracownik == null) {
            return false;
        }

        CurrentPracownik.setCurrentPracownik(pracownik);
        return true;
    }


    private void close() {
        exitButton.setOnAction(SceneController::close);
    }

}

