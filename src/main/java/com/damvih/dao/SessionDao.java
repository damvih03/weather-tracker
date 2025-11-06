package com.damvih.dao;

import com.damvih.entities.Session;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SessionDao {

    private final SessionFactory sessionFactory;

    public Session save(Session session) {
        sessionFactory.getCurrentSession().persist(session);
        return session;
    }

    public Optional<Session> findById(UUID id) {
        Session session = sessionFactory.getCurrentSession().find(Session.class, id);
        return Optional.ofNullable(session);
    }

    public void delete(Session session) {
        sessionFactory.getCurrentSession().remove(session);
    }

}
