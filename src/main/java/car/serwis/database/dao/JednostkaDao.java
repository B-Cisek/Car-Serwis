package car.serwis.database.dao;

import car.serwis.database.model.Jednostka;
import car.serwis.database.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;


import java.util.ArrayList;
import java.util.List;

/**
 * Klasa Data Access Object dla Jednostka
 */
public class JednostkaDao {

    /**
     * Metoda dodająca jednostke
     * @param jednostka przyjmuje obiekt jednostka
     * @return zwraca true jeżeli udane
     */
    public boolean createJednostka(Jednostka jednostka) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(jednostka);
            transaction.commit();
            return transaction.getStatus() == TransactionStatus.COMMITTED;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Metoda usuwająca jednostke
     * @param jednostka przyjmuje obiekt jednostka
     */
    public void deleteJednostka(Jednostka jednostka) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(jednostka);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    /**
     * Metoda aktualizująca jednostke
     * @param jednostka przyjmuje obiekt jednostka
     */
    public void updateJednostka(Jednostka jednostka) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(jednostka);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    /**
     * Metoda pobierająca wszystkie jednostki
     * @return zwraca liste jednostek
     */
    public List getJednostki() {
        Transaction transaction = null;
        List kategorieList = new ArrayList();
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            kategorieList = session.createQuery("FROM Jednostka").list();
        } catch(Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }finally {
            session.close();
        }
        return kategorieList;
    }
}
