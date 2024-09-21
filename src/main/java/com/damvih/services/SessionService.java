package com.damvih.services;

import com.damvih.dao.SessionDao;
import com.damvih.entities.Session;
import com.damvih.entities.User;
import jakarta.servlet.http.Cookie;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class SessionService {

    private static final String SESSION_ID_COOKIE_NAME = "sessionId";
    private final SessionDao sessionDao;

    public SessionService() {
        sessionDao = new SessionDao();
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

    public Optional<Session> getSessionByCookie(Cookie[] cookies) {
        try {
            UUID idValue = getUuidByCookie(cookies);
            return sessionDao.findById(idValue);
        } catch (NoSuchElementException | IllegalArgumentException exception) {
            return Optional.empty();
        }

    }

    public Cookie createCookie(Session session) {
        return new Cookie(SESSION_ID_COOKIE_NAME, session.getId().toString());
    }

    public Cookie createCookieForDelete() {
        Cookie cookie = new Cookie(SESSION_ID_COOKIE_NAME, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        return cookie;
    }

    private UUID getUuidByCookie(Cookie[] cookies) {
        Optional<String> idValue = readCookie(cookies);
        return UUID.fromString(idValue.get());
    }

    private static Optional<String> readCookie(Cookie[] cookies) {
        if (cookies == null || cookies.length < 1) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(c -> SESSION_ID_COOKIE_NAME.equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();
    }

}
