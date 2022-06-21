package car.serwis.database.dao;

import car.serwis.database.model.Faktura;
import car.serwis.database.util.HibernateUtil;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa Data Access Object dla Faktury
 */
public class FakturaDao {

    /**
     * Metoda dodająca fakture do bazy
     * @param faktura przyjmuje obiekt faktura
     * @return zwraca true jeżeli się powiodło
     */
    public boolean createFaktura(Faktura faktura) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(faktura);
            transaction.commit();
            return transaction.getStatus() == TransactionStatus.COMMITTED;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Metoda pobierająca wszystkie faktury
     * @return zwraca liste faktur
     */
    public List displayRecords() {
        List fakturaList = new ArrayList();
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            fakturaList = session.createQuery("FROM Faktura").list();
        } catch (Exception sqlException) {
            if (null != session.getTransaction()) {
                session.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return fakturaList;
    }

    /**
     * Metoda pobiera fakture o danym id
     * @param id przyjmuje id faktury
     * @return zwraca obiekt faktura
     */
    public Faktura getFakturaID(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Faktura.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Metoda usuwająca fakture
     * @param faktura przyjmuje obiekt faktura
     */
    public void deleteFaktura(Faktura faktura) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(faktura);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }
}
