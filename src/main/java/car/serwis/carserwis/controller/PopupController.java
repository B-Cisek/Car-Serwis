package car.serwis.carserwis.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PopupController implements Initializable {

    private static final String POPUPERROR_FXML = "/fxml/popup-error.fxml";
    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;

    @FXML
    private Button closeButton;

    @FXML
    private Text errorMessage;

    @FXML
    private AnchorPane popUpAnchorPane;

    public Stage createErrorPopup(String text){
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(POPUPERROR_FXML));
        Scene scene = null;

        try {
            scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(scene);

        stage.initModality(Modality.APPLICATION_MODAL);

        return stage;
    }

    private Stage getStage(){
        return (Stage) popUpAnchorPane.getScene().getWindow();
    }

    private void initializeCloseButton(){
        closeButton.setOnAction((x) -> {
            getStage().close();
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCloseButton();
    }
}
