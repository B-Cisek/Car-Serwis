package car.serwis.database.dao;

import car.serwis.database.model.Pracownik;
import car.serwis.database.model.Stanowisko;
import car.serwis.database.util.HibernateUtil;
import org.hibernate.Session;

import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class PracownikDao {

    public Pracownik getConnectedPracownik(String login, String haslo) {
       try {
           Session session = HibernateUtil.getSessionFactory().openSession();
           Query<Pracownik> typedQuery = session.createQuery("SELECT u FROM Pracownik u WHERE u.login=:login AND u.haslo=:haslo", Pracownik.class);
           typedQuery.setParameter("login", login);
           typedQuery.setParameter("haslo", haslo);
           return typedQuery.getSingleResult();
       }catch (Exception ex){
           System.err.println("Pracownik not found");
           return null;
       }
    }

    public List<Pracownik> getPracownikStanowisko() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Pracownik ", Pracownik.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }


    public boolean createPracownik(Pracownik pracownik) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(pracownik);
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

    public Pracownik getPracownik(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Pracownik.class, login);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Pracownik> getPracownicy() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Pracownik ", Pracownik.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void deletePracownik(Pracownik pracownik) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(pracownik);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }
}
