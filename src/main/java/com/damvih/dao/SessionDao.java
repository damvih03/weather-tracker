package com.damvih.dao;

import com.damvih.entities.Session;
import com.damvih.entities.User;
import com.damvih.exceptions.DatabaseOperationException;
import com.damvih.utils.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SessionDao extends Dao<Session> {

    public Optional<Session> findById(UUID id) {
        EntityTransaction transaction = null;
        try (EntityManager entityManager = PersistenceUtil.getInstance().createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Session session = entityManager.find(Session.class, id);

            transaction.commit();
            return Optional.ofNullable(session);
        } catch (PersistenceException exception) {
            rollbackTransaction(transaction);
            throw new DatabaseOperationException();
        }
    }

    public void deleteByUser(User user) {
        EntityTransaction transaction = null;
        try (EntityManager entityManager = PersistenceUtil.getInstance().createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager
                    .createQuery("delete from Session s where user=:user")
                    .setParameter("user", user)
                    .executeUpdate();

            transaction.commit();
        } catch (PersistenceException exception) {
            rollbackTransaction(transaction);
            throw new DatabaseOperationException();
        }
    }

    public void deleteIfExpired(LocalDateTime dateTime) {
        EntityTransaction transaction = null;
        try (EntityManager entityManager = PersistenceUtil.getInstance().createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager
                    .createQuery("delete from Session s where expiredAt<=:dateTime")
                    .setParameter("dateTime", dateTime)
                    .executeUpdate();

            transaction.commit();
        } catch (PersistenceException exception) {
            rollbackTransaction(transaction);
            throw new DatabaseOperationException();
        }
    }

}
