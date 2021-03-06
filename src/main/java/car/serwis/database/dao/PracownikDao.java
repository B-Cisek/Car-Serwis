package car.serwis.database.dao;

import car.serwis.database.model.Pracownik;
import car.serwis.database.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa Data Access Object dla Pracownika
 */
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

    // TODO login status
    public Pracownik getConnectedPracownikLogin(String login) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query<Pracownik> typedQuery = session.createQuery("SELECT u FROM Pracownik u WHERE u.login=:login", Pracownik.class);
            typedQuery.setParameter("login", login);
            return typedQuery.getSingleResult();
        }catch (Exception ex){
            System.err.println("Pracownik not found");
            return null;
        }
    }

    public Pracownik getLoginStatus(Long id) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query<Pracownik> typedQuery = session.createQuery("SELECT p  FROM Pracownik p WHERE p.idPracownik = :id", Pracownik.class);
            typedQuery.setParameter("id", id);
            return typedQuery.getSingleResult();
        }catch (Exception ex){
            System.err.println("Pracownik not found");
            return null;
        }
    }

    public Pracownik updateStatus(Long id) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query<Pracownik> typedQuery = session.createQuery("UPDATE Pracownik SET isLoggedIn = true WHERE idPracownik = :id", Pracownik.class);
            typedQuery.setParameter("id", id);
            return typedQuery.getSingleResult();
        }catch (Exception ex){
            System.err.println("Pracownik not found");
            return null;
        }
    }

    public List<Pracownik> getPracownicy() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Pracownik", Pracownik.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }


    public boolean createPracownik(Pracownik pracownik) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(pracownik);
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



    public List getPracownicysss() {
        List pracownicyList = new ArrayList();
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            pracownicyList = session.createQuery("FROM Pracownik").list();
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
        return pracownicyList;
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

    public void updatePracownik(Pracownik pracownik) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(pracownik);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }
}
