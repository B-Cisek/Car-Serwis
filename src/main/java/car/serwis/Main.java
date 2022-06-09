package car.serwis;

import car.serwis.controller.SceneController;
import car.serwis.database.util.HibernateUtil;
import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        SceneController.getInitialScene(stage);
    }

    @Override
    public void init() {
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