package car.serwis.database.dao;

import car.serwis.database.model.*;
import car.serwis.database.util.HibernateUtil;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa Data Access Object dla Części
 */
public class CzescDao {
    /**
     * Metoda dodająca część do bazy
     * @param czesc przyjmuje obiekt Część
     * @return zwraca true jeżeli operacja się udała
     */
    public boolean createCzesc(Czesc czesc) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(czesc);
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
     * Metoda pobiera liste części
     * @return zwraca Liste części
     */
    public List displayRecords() {
        List czesciList = new ArrayList();
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            czesciList = session.createQuery("FROM Czesc").list();
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
        return czesciList;
    }

    /**
     * Metoda pobiera liste części
     * @return zwraca ArrayListe części
     */
    public List<Czesc> getCzesci() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Czesc", Czesc.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Metoda usuwa część
     * @param czesc przyjmuje obiekt część
     */
    public void deleteCzesc(Czesc czesc) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(czesc);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    /**
     * Metoda aktualizująca część
     * @param czesc przyjmuje obiekt część
     */
    public void updateCzesc(Czesc czesc) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(czesc);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    /**
     * Metoda pobierajaąca liste części dla danej kategori
     * @param kategoria przyjmuje obiekt kategoria
     * @return zwraca ArrayListe części
     */
    public List<Czesc> getCzescForKategoria(Kategoria kategoria) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            TypedQuery<Czesc> query = session.createQuery("SELECT c FROM Czesc c WHERE c.kategoria = :kategoria", Czesc.class);
            query.setParameter("kategoria", kategoria);
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Pobiera część o podanym id
     * @param czesc przyjmuje obiekt część
     * @return zwraca pojedyńczy obiekt
     */
    public Czesc getCzesc(Czesc czesc) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query<Czesc> typedQuery = session.createQuery("SELECT cz FROM Czesc cz WHERE cz.idCzesc = :id", Czesc.class);
            typedQuery.setParameter("id", czesc.getIdCzesc());
            return typedQuery.getSingleResult();
        }catch (Exception ex){
            System.err.println("Czesc not found");
            return null;
        }
    }

}
