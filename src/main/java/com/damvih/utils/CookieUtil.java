package com.damvih.utils;

import com.damvih.entities.Session;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class CookieUtil {

    private static final String SESSION_ID_COOKIE_NAME = "sessionId";

    public static Cookie createCookie(Session session) {
        return new Cookie(SESSION_ID_COOKIE_NAME, session.getId().toString());
    }

    public static void deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(SESSION_ID_COOKIE_NAME, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static Optional<UUID> getUuidByCookie(Cookie[] cookies) {
        try {
            Optional<String> idValue = readCookie(cookies);
            return Optional.of(UUID.fromString(idValue.get()));
        } catch (Exception exception) {
            return Optional.empty();
        }

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

    private CookieUtil() {

    }

}
