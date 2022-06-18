package car.serwis.database.dao;

import car.serwis.database.model.Zlecenie;
import car.serwis.database.util.HibernateUtil;
import car.serwis.helpers.CurrentPracownik;
import car.serwis.helpers.ZlecenieStatus;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.ArrayList;
import java.util.List;

public class ZlecenieDao {
    public boolean createZlecenie(Zlecenie zlecenie) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(zlecenie);
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

    public List<Zlecenie> getZlecenie() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Zlecenie", Zlecenie.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }



    public List<Zlecenie> getMechanikZlecenia() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            TypedQuery<Zlecenie> query = session.createQuery("SELECT z FROM Zlecenie z WHERE z.pracownik = :id", Zlecenie.class );
            query.setParameter("id", CurrentPracownik.getCurrentPracownik());
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Zlecenie> getDostepneZlecenia() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            TypedQuery<Zlecenie> query = session.createQuery("SELECT z FROM Zlecenie z WHERE z.pracownik IS NULL", Zlecenie.class );
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void deleteZlecenie(Zlecenie zlecenie) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(zlecenie);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }


    public Long getNoweSprawy() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<?> query = session.createQuery("SELECT count(z) FROM Zlecenie z WHERE z.status = :status");
            query.setParameter("status",ZlecenieStatus.NOWE);
            return (Long) query.uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }

    public Long getOczekujaceSprawy() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<?> query = session.createQuery("SELECT count(z) FROM Zlecenie z WHERE z.status = :status");
            query.setParameter("status",ZlecenieStatus.OCZEKUJACE);
            return (Long) query.uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }

    public Long getWtrakcieSprawy() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<?> query = session.createQuery("SELECT count(z) FROM Zlecenie z WHERE z.status = :status");
            query.setParameter("status",ZlecenieStatus.W_TRAKCIE);
            return (Long) query.uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }

    public Long getGotoweSprawy() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<?> query = session.createQuery("SELECT count(z) FROM Zlecenie z WHERE z.status = :status");
            query.setParameter("status",ZlecenieStatus.GOTOWE);
            return (Long) query.uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0L;
        }
    }

    public boolean updateZlecenie(Zlecenie zlecenie) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(zlecenie);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return false;
    }

    public List<Zlecenie> getRecentNoweZlecenia() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            TypedQuery<Zlecenie> query = session.createQuery("SELECT z FROM Zlecenie z WHERE z.status = :status ORDER BY z.idZlecenie DESC", Zlecenie.class );
            query.setMaxResults(5);
            query.setParameter("status", ZlecenieStatus.NOWE);
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Zlecenie> getRecentGotoweZlecenia() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            TypedQuery<Zlecenie> query = session.createQuery("SELECT z FROM Zlecenie z WHERE z.status = :status ORDER BY z.idZlecenie DESC", Zlecenie.class );
            query.setMaxResults(5);
            query.setParameter("status", ZlecenieStatus.GOTOWE);
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

}
