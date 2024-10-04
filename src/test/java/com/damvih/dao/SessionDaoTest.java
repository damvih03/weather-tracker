package com.damvih.dao;

import com.damvih.entities.Session;
import com.damvih.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class SessionDaoTest {

    private static SessionDao sessionDao;
    private static User user;

    @BeforeAll
    public static void setup() {
        sessionDao = new SessionDao();

        UserDao userDao = new UserDao();

        user = userDao.save(
                User.builder()
                        .login("userSession")
                        .password("password")
                        .build()
        );
    }

    @Test
    public void testDeletingActualSession() {
        UUID sessionId = UUID.randomUUID();

        sessionDao.save(new Session(
                sessionId,
                user,
                LocalDateTime.now().plusHours(6)
        ));

        sessionDao.deleteIfExpired(LocalDateTime.now());

        Optional<Session> session = sessionDao.findById(sessionId);
        Assertions.assertTrue(session.isPresent());
    }

    @Test
    public void testDeletingExpiredSession() {
        UUID sessionId = UUID.randomUUID();

        sessionDao.save(new Session(
                sessionId,
                user,
                LocalDateTime.now().minusHours(6 + 1)
        ));

        sessionDao.deleteIfExpired(LocalDateTime.now());

        Optional<Session> session = sessionDao.findById(sessionId);
        Assertions.assertTrue(session.isEmpty());
    }

}
