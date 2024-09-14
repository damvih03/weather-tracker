package com.damvih.servlets;

import com.damvih.dao.SessionDao;
import com.damvih.entities.Session;
import com.damvih.entities.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

abstract public class BaseServlet extends HttpServlet {

    public static final String SESSION_ID_COOKIE_NAME = "sessionId";
    private SessionDao sessionDao = new SessionDao();

    protected Optional<Session> getSessionByCookie(HttpServletRequest request) {
        Optional<String> idValue = readCookie(request);
        if (idValue.isEmpty()) {
            return Optional.empty();
        }
        UUID id = UUID.fromString(idValue.get());
        return sessionDao.findById(id);
    }

    protected Session createSession(User user) {
        Session session = new Session(
                UUID.randomUUID(),
                user,
                LocalDateTime.now().plusHours(6)
        );
        return sessionDao.save(session);
    }

    protected void addCookie(HttpServletResponse response, Session session) {
        Cookie cookie = new Cookie("sessionId", session.getId().toString());
        response.addCookie(cookie);
    }

    protected SessionDao getSessionDao() {
        return sessionDao;
    }

    private Optional<String> readCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length < 1) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(c -> SESSION_ID_COOKIE_NAME.equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();
    }

}
