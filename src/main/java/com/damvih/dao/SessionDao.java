package com.damvih.dao;

import com.damvih.entities.Session;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SessionDao extends BaseDao<Session> {

    private final SessionFactory sessionFactory;

    public Optional<Session> findById(UUID id) {
        Session session = sessionFactory.getCurrentSession().find(Session.class, id);
        return Optional.ofNullable(session);
    }

}
