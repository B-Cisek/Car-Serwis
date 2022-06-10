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

    public static void getNewZlecenieWindow() throws IOException {
        getPopUpWindow(ScenePath.ADD_ZLECENIE.getPath());
    }

    public static void getNewSamochodWindow() throws IOException {
        getPopUpWindow(ScenePath.ADD_SAMOCHOD.getPath());
    }

    public static void getNewKategoriaWindow() throws IOException {
        getPopUpWindow(ScenePath.ADD_KATEGORIA.getPath());
    }

    public static void getNewJednostkaWindow() throws IOException {
        getPopUpWindow(ScenePath.ADD_JEDNOSTKA.getPath());
    }

    public static void getNewKontrahentWindow() throws IOException {
        getPopUpWindow(ScenePath.ADD_KONTRAHENT.getPath());
    }

    public static void getNewCzescWindow() throws IOException {
        getPopUpWindow(ScenePath.ADD_CZESC.getPath());
    }

    public static void getNewStatusWindow() throws IOException {
        getPopUpWindow(ScenePath.UPDATE_STATUS.getPath());
    }

    public static void getNewFakturaWindow() throws IOException {
        getPopUpWindow(ScenePath.ADD_FAKTURA.getPath());
    }

    public static void getNewPozycjaWindow() throws IOException {
        getPopUpWindow(ScenePath.ADD_POZYCJA.getPath());
    }


}
