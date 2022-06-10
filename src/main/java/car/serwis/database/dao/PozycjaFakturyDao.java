package car.serwis.database.dao;

import car.serwis.database.model.PozycjaFaktury;
import car.serwis.database.model.Pracownik;
import car.serwis.database.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class PozycjaFakturyDao {
    public boolean createPozycjaFaktury(PozycjaFaktury pozycjaFaktury) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(pozycjaFaktury);
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
}
