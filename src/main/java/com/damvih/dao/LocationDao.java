package com.damvih.dao;

import com.damvih.entities.Location;
import com.damvih.exceptions.DatabaseOperationException;
import com.damvih.utils.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;

import java.math.BigDecimal;
import java.util.Optional;

public class LocationDao extends Dao<Location> {

    public Optional<Location> findByCoordinates(BigDecimal longitude, BigDecimal latitude) {
        EntityTransaction transaction = null;
        try (EntityManager entityManager = PersistenceUtil.getInstance().createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Location location = entityManager
                    .createQuery("select l from Location l where longitude=:longitude and latitude=:latitude", Location.class)
                    .setParameter("longitude", longitude)
                    .setParameter("latitude", latitude)
                    .getSingleResult();

            transaction.commit();
            return Optional.of(location);
        } catch (NoResultException exception) {
            return Optional.empty();
        } catch (PersistenceException exception) {
            rollbackTransaction(transaction);
            throw new DatabaseOperationException();
        }
    }

    @Override
    protected String getConstraintViolationExceptionMessage() {
        return "Location with these coordinates already exists!";
    }

}
