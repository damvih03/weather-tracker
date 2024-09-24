package com.damvih.servlets.auth;

import com.damvih.entities.Session;
import com.damvih.services.SessionService;
import com.damvih.services.UserService;
import com.damvih.utils.CookieUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

abstract public class BaseAuthServlet extends HttpServlet {

    protected UserService userService;
    protected SessionService sessionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = new UserService();
        sessionService = new SessionService();
    }

    protected void addCookie(HttpServletResponse response, Session session) {
        response.addCookie(CookieUtil.createCookie(session));
    }

}
