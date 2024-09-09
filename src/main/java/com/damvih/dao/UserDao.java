package com.damvih.dao;

import com.damvih.entities.User;
import com.damvih.exceptions.DatabaseOperationException;
import com.damvih.exceptions.UserAlreadyExistsException;
import com.damvih.utils.PersistenceUtil;
import jakarta.persistence.*;

import java.util.Optional;

public class UserDao extends Dao<User> {

    public Optional<User> findByLogin(String login) {
        EntityTransaction transaction = null;
        try (EntityManager entityManager = PersistenceUtil.getInstance().createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();

            User user = entityManager
                    .createQuery("select u from User u where login=:login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();

            transaction.commit();
            return Optional.of(user);
        } catch (NoResultException exception) {
            return Optional.empty();
        } catch (PersistenceException exception) {
            rollbackTransaction(transaction);
            throw new DatabaseOperationException();
        }
    }

    @Override
    protected void handleConstraintViolationException() {
        throw new UserAlreadyExistsException();
    }

}
