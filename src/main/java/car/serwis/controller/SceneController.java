package car.serwis.controller;

import car.serwis.database.dao.PracownikDao;
import car.serwis.database.model.Pracownik;
import car.serwis.helpers.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;


public class SceneController {

    private static double x;
    private static double y;
    private static final String TITLE = "Car Serwis";
    private static final int WIDTH = 600;
    private static final int HEIGHT = 550;
    private static Parent main;


    public static void getInitialScene(Stage stage) throws IOException {
        main = FXMLLoader.load((SceneController.class.getResource(ScenePath.LOGIN.getPath())));
        Scene scene = new Scene(main, WIDTH, HEIGHT);
        controlDrag(stage);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void getPulpitScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.PULPIT.getPath());
    }

    public static void getZleceniaScene(ActionEvent event) throws IOException {
        if (CurrentPracownik.getCurrentPracownik().getStanowisko().getUprawnienia().equals(UsersPermissions.OBSLUGA_KLIENTA) ||
                CurrentPracownik.getCurrentPracownik().getStanowisko().getUprawnienia().equals(UsersPermissions.ADMIN)){
            changeScreen(event, ScenePath.ZLECENIA.getPath());
        }else {
            AlertPopUp.successAlert("Nie masz uprawnień do panelu: ZLECENIA");
        }
    }

    public static void getWarsztatScene(ActionEvent event) throws IOException {
        if (CurrentPracownik.getCurrentPracownik().getStanowisko().getUprawnienia().equals(UsersPermissions.MECHANIK) ||
            CurrentPracownik.getCurrentPracownik().getStanowisko().getUprawnienia().equals(UsersPermissions.ADMIN)){
            changeScreen(event, ScenePath.WARSZTAT.getPath());
        }else {
            AlertPopUp.successAlert("Nie masz uprawnień do panelu: WARSZTAT");
        }
    }

    public static void getKsiegowoscScene(ActionEvent event) throws IOException {
        if  (CurrentPracownik.getCurrentPracownik().getStanowisko().getUprawnienia().equals(UsersPermissions.OBSLUGA_KLIENTA) ||
                CurrentPracownik.getCurrentPracownik().getStanowisko().getUprawnienia().equals(UsersPermissions.ADMIN)){
            changeScreen(event, ScenePath.KSIEGOWOSC.getPath());
        }else {
            AlertPopUp.successAlert("Nie masz uprawnień do panelu: KSIĘGOWOŚĆ");
        }
    }

    public static void getMagazynScene(ActionEvent event) throws IOException {
        if (CurrentPracownik.getCurrentPracownik().getStanowisko().getUprawnienia().equals(UsersPermissions.MAGAZYNIER) ||
            CurrentPracownik.getCurrentPracownik().getStanowisko().getUprawnienia().equals(UsersPermissions.ADMIN) ||
            CurrentPracownik.getCurrentPracownik().getStanowisko().getUprawnienia().equals(UsersPermissions.MECHANIK)){
            changeScreen(event, ScenePath.MAGAZYN.getPath());
        }else {
            AlertPopUp.successAlert("Nie masz uprawnień do panelu: MAGAZYN");
        }
    }

    public static void getUstawieniaScene(ActionEvent event) throws IOException {
        if (CurrentPracownik.getCurrentPracownik().getStanowisko().getUprawnienia().equals(UsersPermissions.ADMIN)){
            changeScreen(event, ScenePath.USTAWIENIA.getPath());
        }else {
            AlertPopUp.successAlert("Nie masz uprawnień do panelu: USTAWIENIA");
        }
    }

    public static void getPomocScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.POMOC.getPath());
    }

    private static void changeScreen(ActionEvent event, String path) throws IOException {
        main = FXMLLoader.load(SceneController.class.getResource(path));
        Scene pilpitScene = new Scene(main);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(pilpitScene);

        final double CENTER_ON_SCREEN_X_FRACTION = 1.0f / 2;
        final double CENTER_ON_SCREEN_Y_FRACTION = 1.0f / 3;

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

        double centerX = bounds.getMinX() + (bounds.getWidth() - window.getWidth())
                * CENTER_ON_SCREEN_X_FRACTION;
        double centerY = bounds.getMinY() + (bounds.getHeight() - window.getHeight())
                * CENTER_ON_SCREEN_Y_FRACTION;

        window.setX(centerX);
        window.setY(centerY);
        //TODO zmiana centrowania
        controlDrag(window);
        window.show();
    }


    public static void controlDrag(Stage stage) {
        main.setOnMousePressed(event -> {
            x = stage.getX() - event.getScreenX();
            y = stage.getY() - event.getScreenY();
        });
        main.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + x);
            stage.setY(event.getScreenY() + y);

        });
    }

    public static void close(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}

