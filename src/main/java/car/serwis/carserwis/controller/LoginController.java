package car.serwis.carserwis.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private static final String APP_FXML = "/fxml/app.fxml";
    private static final String APP_TITLE = "Car Serwis";

    private PopupController popupController;

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


    public LoginController(){
        popupController = new PopupController();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeExitButton();
        initializeLoginButton();
    }


    private void initializeExitButton(){
        exitButton.setOnAction((x) -> {
            getStage().close();
        });
    }

    private void initializeLoginButton() {
        loginButton.setOnAction((x) -> {
            AuthenticationUser();
        });
    }

    private Stage getStage(){
        return (Stage) loginAnchorPane.getScene().getWindow();
    }

    private void openAppAndCloseLoginStage(){
        Stage appStage = new Stage();
        Parent appRoot = null;

        try {
            appRoot = FXMLLoader.load(getClass().getResource(APP_FXML));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(appRoot,1600,900);
        appStage.setTitle(APP_TITLE);
        appStage.setScene(scene);
        appStage.show();
        getStage().close();
    }

    private void AuthenticationUser(){
        String login = loginTextField.getText();
        String haslo = hasloTextField.getText();

        if (login.equals("admin") && haslo.equals("admin")){
            openAppAndCloseLoginStage();
        }else {
            Stage errorPopup = popupController.createErrorPopup("Błędne dane");
            errorPopup.show();
        }
    }

}

