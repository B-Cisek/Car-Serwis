package car.serwis.database.dao;

import car.serwis.database.model.Pracownik;
import car.serwis.database.util.HibernateUtil;
import org.hibernate.Session;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.Transaction;

public class PracownikDao {
    public Pracownik getConnectedPracownik(String login, String haslo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            TypedQuery<Pracownik> query = session.createQuery("SELECT u FROM Pracownik u WHERE login = :name AND haslo = :pass", Pracownik.class );
            query.setParameter("name", login);
            query.setParameter("pass", haslo);
            return query.getSingleResult();
        } catch (NoResultException ex) {
            System.err.println("Pracownik not found");
            return null;
        }
    }

    public void createPracownik(Pracownik pracownik) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(pracownik);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public Pracownik getPracownik(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Pracownik.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
