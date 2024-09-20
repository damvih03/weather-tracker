package com.damvih.utils;

import com.damvih.entities.Session;
import com.damvih.exceptions.SessionNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class CookieUtil {

    private static final String SESSION_ID_COOKIE_NAME = "sessionId";

    public static UUID getUuidByCookie(HttpServletRequest request) {
        String idValue = readCookie(request).orElseThrow(SessionNotFoundException::new);
        try {
            return UUID.fromString(idValue);
        } catch (Exception exception) {
            throw new SessionNotFoundException();
        }
    }

    public static void deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(SESSION_ID_COOKIE_NAME, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static void addCookie(HttpServletResponse response, Session session) {
        Cookie cookie = new Cookie("sessionId", session.getId().toString());
        response.addCookie(cookie);
    }

    private static Optional<String> readCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length < 1) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(c -> SESSION_ID_COOKIE_NAME.equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();
    }

    private CookieUtil() {

    }

}
