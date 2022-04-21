package car.serwis.controller;

import car.serwis.helpers.ScenePath;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;

/**
 * Code created by Andrius on 2020-09-29
 */
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
        changeScreen(event, ScenePath.ZLECENIA.getPath());
    }

    public static void getWarsztatScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.WARSZTAT.getPath());
    }

    public static void getKsiegowoscScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.KSIEKOWOSC.getPath());
    }

    public static void getMagazynScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.MAGAZYN.getPath());
    }

    public static void getUstawieniaScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.USTAWIENIA.getPath());
    }

    public static void getPomocScene(ActionEvent event) throws IOException {
        changeScreen(event, ScenePath.POMOC.getPath());
    }

    private static void changeScreen(ActionEvent event, String path) throws IOException {
        main = FXMLLoader.load(SceneController.class.getResource(path));
        Scene visitScene = new Scene(main);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controlDrag(window);
        window.setScene(visitScene);
        window.show();
    }

    public static void controlDrag(Stage stage) {
        main.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                x = stage.getX() - event.getScreenX();
                y = stage.getY() - event.getScreenY();
            }
        });
        main.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + x);
                stage.setY(event.getScreenY() + y);
            }
        });
    }

    public static void close(ActionEvent actionEvent) {
        Node  source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}

