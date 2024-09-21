package com.damvih.filters;

import com.damvih.entities.Session;
import com.damvih.services.SessionService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
        sessionService = new SessionService();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Optional<Session> session = sessionService.getSessionByCookie(request.getCookies());

        String path = request.getRequestURI();
        if (!allowedUrlsWithoutSession.contains(path)) {

            if (session.isPresent()) {
                request.setAttribute(SESSION_ATTRIBUTE_NAME, session.get());
                return;
            }
            response.sendRedirect("/sign-in");

        } else {

            if (session.isPresent()) {
                request.setAttribute(SESSION_ATTRIBUTE_NAME, session.get());
                request.getRequestDispatcher("/home").forward(request, response);
            }

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
