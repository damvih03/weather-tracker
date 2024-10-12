package com.damvih.dao;

import com.damvih.exceptions.DatabaseOperationException;
import com.damvih.exceptions.EntityAlreadyExistsException;
import com.damvih.utils.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;

abstract public class Dao<T> {

    public void delete(T entity) {
        EntityTransaction transaction = null;
        try (EntityManager entityManager = PersistenceUtil.getInstance().createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(entity);
            transaction.commit();
        } catch (PersistenceException exception) {
            rollbackTransaction(transaction);
            throw new DatabaseOperationException();
        }
    }

    public T save(T entity) {
        EntityTransaction transaction = null;
        try (EntityManager entityManager = PersistenceUtil.getInstance().createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
            return entity;
        } catch (PersistenceException exception) {
            rollbackTransaction(transaction);
            if (isConstraintViolationException(exception)) {
                throw new EntityAlreadyExistsException(getConstraintViolationExceptionMessage());
            }
            throw new DatabaseOperationException();
        }
    }

    abstract protected String getConstraintViolationExceptionMessage();

    protected void rollbackTransaction(EntityTransaction transaction) {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
    }

    private boolean isConstraintViolationException(PersistenceException exception) {
        return exception instanceof ConstraintViolationException;
    }

}
