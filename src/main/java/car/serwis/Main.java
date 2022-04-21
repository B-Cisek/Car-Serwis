package car.serwis;

import car.serwis.controller.SceneController;
import car.serwis.database.util.HibernateUtil;
import car.serwis.helpers.ScenePath;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;

import java.io.IOException;

public class Main extends Application {

    private static final String TITLE = "Car Serwis";
    private static final int WIDTH = 600;
    private static final int HEIGHT = 550;


    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ScenePath.LOGIN.getPath()));
//        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
//        stage.initStyle(StageStyle.TRANSPARENT);
//        stage.setScene(scene);
//        scene.setFill(Color.TRANSPARENT);
//        stage.setTitle(TITLE);
//        stage.setScene(scene);
//        stage.show();
        SceneController.getInitialScene(stage);
    }

    @Override
    public void init() throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.close();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        HibernateUtil.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}