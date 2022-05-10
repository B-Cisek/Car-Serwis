package car.serwis.database.dao;

import car.serwis.database.model.Pracownik;
import car.serwis.database.model.Zlecenie;
import car.serwis.database.util.HibernateUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class ZlecenieDao {

    public List<Zlecenie> getZlecenia() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Zlecenie ", Zlecenie.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }
}
