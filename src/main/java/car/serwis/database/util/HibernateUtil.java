package car.serwis.database.util;

import car.serwis.database.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    public static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://78.46.91.52/bartlom1_serwis?serverTimezone=UTC");
                settings.put(Environment.USER, "bartlom1_serwis");
                settings.put(Environment.PASS, "serwis");
                settings.put(Environment.POOL_SIZE,"200");
                settings.put(Environment.AUTO_CLOSE_SESSION,"true");
                //settings.put(Environment.AUTOCOMMIT,"true");

                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(Stanowisko.class);
                configuration.addAnnotatedClass(Jednostka.class);
                configuration.addAnnotatedClass(Kategoria.class);
                configuration.addAnnotatedClass(Samochod.class);
                configuration.addAnnotatedClass(Pracownik.class);
                configuration.addAnnotatedClass(Kontrahent.class);
                configuration.addAnnotatedClass(Czesc.class);
                configuration.addAnnotatedClass(Zlecenie.class);
                configuration.addAnnotatedClass(Faktura.class);
                configuration.addAnnotatedClass(PozycjaFaktury.class);


                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
