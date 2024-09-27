package com.damvih.filters;

import com.damvih.entities.Session;
import com.damvih.services.SessionService;
import com.damvih.utils.CookieUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@WebFilter("/*")
public class AuthFilter implements Filter {

    public static final String SESSION_ATTRIBUTE_NAME = "session";
    private static final List<String> allowedUrlsWithoutSession = List.of(
            "/sign-in",
            "/sign-up"
    );
    private SessionService sessionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        sessionService = (SessionService) filterConfig
                .getServletContext()
                .getAttribute("SessionService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Optional<UUID> id = CookieUtil.getUuidByCookie(request.getCookies());

        Optional<Session> session = Optional.empty();
        if (id.isPresent()) {
            session = sessionService.findById(id.get());
        }

        String path = request.getRequestURI();
        if (!allowedUrlsWithoutSession.contains(path)) {

            if (session.isPresent()) {
                request.setAttribute(SESSION_ATTRIBUTE_NAME, session.get());
            } else {
                response.sendRedirect("/sign-in");
                return;
            }

        } else {

            if (session.isPresent()) {
                request.setAttribute(SESSION_ATTRIBUTE_NAME, session.get());
                request.getRequestDispatcher("/home").forward(request, response);
            }

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
