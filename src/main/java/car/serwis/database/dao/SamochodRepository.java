package car.serwis.database.dao;

import car.serwis.database.model.Samochod;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class SamochodRepository {
    private EntityManager entityManager;

    public SamochodRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public SamochodRepository() {
    }

    public Optional<Samochod> save(Samochod samochod) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(samochod);
            entityManager.getTransaction().commit();
            return Optional.of(samochod);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
