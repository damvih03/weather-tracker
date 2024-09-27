package com.damvih.services;

import com.damvih.dao.SessionDao;
import com.damvih.entities.Session;
import com.damvih.entities.User;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class SessionService {

    private final SessionDao sessionDao;

    public SessionService(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    public Session save(User user) {
        Session session = new Session(
                UUID.randomUUID(),
                user,
                LocalDateTime.now().plusHours(6)
        );
        return sessionDao.save(session);
    }

    public void delete(Session session) {
        sessionDao.delete(session);
    }

    public Optional<Session> findById(UUID id) {
        return sessionDao.findById(id);
    }

}
