package car.serwis.database.dao;

import car.serwis.database.model.Pracownik;
import car.serwis.database.model.Samochod;
import car.serwis.database.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.ArrayList;
import java.util.List;

public class SamochodDao {

    public boolean createSamochod(Samochod samochod) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(samochod);
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

    public void deleteSamochod(Samochod samochod) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(samochod);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public List getSamochody() {
        Transaction transaction = null;
        List samochodyList = new ArrayList();
        try {
            // Getting Session Object From SessionFactory
            Session session = HibernateUtil.getSessionFactory().openSession();
            // Getting Transaction Object From Session Object
            transaction = session.beginTransaction();

            samochodyList = session.createQuery("FROM Samochod").list();
        } catch(Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }

        return samochodyList;
    }
}
