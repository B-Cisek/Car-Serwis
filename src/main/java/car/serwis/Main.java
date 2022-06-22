package car.serwis;

import car.serwis.controller.SceneController;
import car.serwis.database.util.HibernateUtil;
import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.io.IOException;

/**
 * Główna klasa aplikacji
 * @author Bartłomiej Cisek
 * @version 1.0.0
 */
public class Main extends Application {

    /**
     * Metoda uruchamiająca aplikacje, inicjalizująca "LOGIN" scene
     *
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        SceneController.getInitialScene(stage);
    }

    /**
     * Metoda inicjalizująca połącznie z bazą danych
     */
    @Override
    public void init() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.close();
    }

    /**
     * Metoda kończąca działanie aplikacji
     *
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        HibernateUtil.shutdown();
    }

    /**
     * Główna metoda klasy Main
     *
     * @param args zawiera argumenty wiersza poleceń przekazywane do programu Java po wywołaniu
     */
    public static void main(String[] args) {
        launch(args);
    }
}