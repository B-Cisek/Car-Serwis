package car.serwis.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewLoginController implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeExitButton();
    }

    @FXML
    private void loginUser(ActionEvent event) throws IOException, InterruptedException {
//        String user = username.getText();
//        String pass = password.getText();
//
//        if(!validFields()) {
//            infoLine.setText("Username and password can't be empty!");
//            return;
//        }
//
//        if (!validateLogin()) {
//            infoLine.setText("User not found!");
//            return;
//        }
//
//        welcome.setText("Welcome, " + CurrentUser.getCurrentUser().getUserName() + "!");
//        infoLine.setText("Redirecting to main dashboard...");

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


    private void initializeExitButton(){
        exitButton.setOnAction((x) -> {
            getStage().close();
        });
    }

    private Stage getStage(){
        return (Stage) loginAnchorPane.getScene().getWindow();
    }

}
