package com.damvih.servlets;

import com.damvih.dao.SessionDao;
import com.damvih.entities.Session;
import com.damvih.exceptions.SessionNotFoundException;
import com.damvih.utils.CookieUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/sign-out")
public class SignOutServlet extends HttpServlet {

    private SessionDao sessionDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        sessionDao = new SessionDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = sessionDao
                .findById(CookieUtil.getUuidByCookie(request))
                .orElseThrow(SessionNotFoundException::new);
        sessionDao.delete(session);
        CookieUtil.deleteCookie(response);
        response.sendRedirect("/sign-in");
    }

}
