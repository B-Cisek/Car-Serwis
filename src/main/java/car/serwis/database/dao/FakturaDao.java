package car.serwis.database.dao;

import car.serwis.database.model.Faktura;
import car.serwis.database.model.Pracownik;
import car.serwis.database.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.ArrayList;
import java.util.List;

public class FakturaDao {

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

    public List displayRecords() {
        List fakturaList = new ArrayList();
        Session session = null;
        try {
            // Getting Session Object From SessionFactory
            session = HibernateUtil.getSessionFactory().openSession();
            // Getting Transaction Object From Session Object
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
}
