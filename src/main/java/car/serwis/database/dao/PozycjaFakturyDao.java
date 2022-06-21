package car.serwis.database.dao;

import car.serwis.database.model.Faktura;
import car.serwis.database.model.PozycjaFaktury;
import car.serwis.database.util.HibernateUtil;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa Data Access Object dla Pozycji Faktury
 */
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

    public List<PozycjaFaktury> getPozycjaFakturyForPdf(Faktura faktura) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            TypedQuery<PozycjaFaktury> query = session.createQuery("SELECT pf FROM PozycjaFaktury pf WHERE pf.faktura = :id", PozycjaFaktury.class);
            query.setParameter("id", faktura);
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }
}
