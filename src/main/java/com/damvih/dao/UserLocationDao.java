package com.damvih.dao;

import com.damvih.entities.Location;
import com.damvih.entities.User;
import com.damvih.entities.UserLocation;
import com.damvih.exceptions.DatabaseOperationException;
import com.damvih.utils.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import java.util.List;

public class UserLocationDao extends Dao<UserLocation> {

    public List<Location> findLocationsByUser(User user) {
        EntityTransaction transaction = null;
        try (EntityManager entityManager = PersistenceUtil.getInstance().createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();

            List<Location> locations = entityManager
                    .createQuery("""
                            select l from Location l
                            join UserLocation ul on ul.userLocationCompositeKey.location = l
                            where ul.userLocationCompositeKey.user=:user
                            """,
                            Location.class)
                    .setParameter("user", user)
                    .getResultList();

            transaction.commit();
            return locations;
        } catch (PersistenceException exception) {
            rollbackTransaction(transaction);
            throw new DatabaseOperationException();
        }
    }

}
