package com.damvih.servlets.auth;

import com.damvih.entities.Session;
import com.damvih.services.SessionService;
import com.damvih.services.UserService;
import com.damvih.servlets.BaseServlet;
import com.damvih.utils.CookieUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

abstract public class AuthServlet extends BaseServlet {

    protected UserService userService;
    protected SessionService sessionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config
                .getServletContext()
                .getAttribute("UserService");

        sessionService = (SessionService) config
                .getServletContext()
                .getAttribute("SessionService");

        super.init(config);
    }

    protected void addCookie(HttpServletResponse response, Session session) {
        response.addCookie(CookieUtil.createCookie(session));
    }

}
