package com.damvih.controller.util;

import com.damvih.dto.SessionDto;
import com.damvih.exception.InvalidSessionException;
import com.damvih.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SessionInterceptor implements HandlerInterceptor {

    private static final String SESSION_ID_COOKIE_NAME = "SESSION_ID";
    private final SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UUID id = getIdFromCookie(request.getCookies())
                .orElseThrow(() -> new InvalidSessionException("Invalid session cookie."));
        SessionDto sessionDto = sessionService.getValid(id);
        request.setAttribute("session", sessionDto);
        return true;
    }

    private Optional<UUID> getIdFromCookie(Cookie[] cookies) {
        if (cookies == null) {
            return Optional.empty();
        }

        for (Cookie cookie : cookies) {
            if (SESSION_ID_COOKIE_NAME.equals(cookie.getName())) {
                return extractIdFromString(cookie.getValue());
            }
        }

        return Optional.empty();
    }

    private Optional<UUID> extractIdFromString(String id) {
        try {
            return Optional.of(UUID.fromString(id));
        } catch (IllegalArgumentException exception) {
            return Optional.empty();
        }
    }

}
