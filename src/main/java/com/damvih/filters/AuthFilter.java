package com.damvih.filters;

import com.damvih.dao.SessionDao;
import com.damvih.entities.Session;
import com.damvih.exceptions.SessionNotFoundException;
import com.damvih.utils.CookieUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@WebFilter(urlPatterns = {"/index.html"})
public class AuthFilter implements Filter {

    public static final String SESSION_ID_COOKIE_NAME = "sessionId";
    private SessionDao sessionDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        sessionDao = new SessionDao();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            UUID id = CookieUtil.getUuidByCookie(request);
            Session session = sessionDao.findById(id).orElseThrow(SessionNotFoundException::new);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (SessionNotFoundException exception) {

            ((HttpServletResponse)servletResponse).sendRedirect("sign-in.html");
        }

    }

}
