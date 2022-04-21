package car.serwis.controller;

import car.serwis.helpers.ScenePath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class NewWindowController {

    public static void getPopUpWindow(String path) throws IOException {
        Stage stage = new Stage();
        Pane main = FXMLLoader.load(NewWindowController.class.getResource(path));
        stage.setScene(new Scene(main));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Car Serwis");
        stage.getScene();
        stage.showAndWait();
    }

    public static void getNewStanowiskoWindow() throws IOException {
        getPopUpWindow(ScenePath.ADD_STANOWISKO.getPath());
    }

    public static void getNewPracownikWindow() throws IOException {
        getPopUpWindow(ScenePath.ADD_PRACOWNIK.getPath());
    }

}
