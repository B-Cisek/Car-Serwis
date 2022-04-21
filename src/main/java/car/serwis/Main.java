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

    @Override
    public void start(Stage stage) throws IOException {
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