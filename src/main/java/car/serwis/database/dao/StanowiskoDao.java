package car.serwis.database.dao;

import car.serwis.database.model.Stanowisko;
import car.serwis.database.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.ArrayList;
import java.util.List;

public class StanowiskoDao {

//    public boolean createStanowisko(Stanowisko stanowisko) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//
//            transaction = session.beginTransaction();
//
//            session.persist(stanowisko);
//
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//        return true;
//    }


    public boolean createStanowisko(Stanowisko stanowisko) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(stanowisko);
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

    public void updateStanowisko(Stanowisko stanowisko) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(stanowisko);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public void deleteStanowisko(Stanowisko stanowisko) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(stanowisko);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public Stanowisko getStanowisko(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Stanowisko.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Stanowisko> getStanowiska() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Stanowisko", Stanowisko.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }


    public Long getStanowiskoCount() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<?> query = session.createQuery("SELECT count(v) FROM Stanowisko v");
            return (Long) query.uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }
}