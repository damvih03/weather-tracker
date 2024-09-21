package com.damvih.servlets.auth;

import com.damvih.entities.Session;
import com.damvih.filters.AuthFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/sign-out")
public class SignOutServlet extends BaseAuthServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = (Session) request.getServletContext().getAttribute(AuthFilter.SESSION_ATTRIBUTE_NAME);
        sessionService.delete(session);
        deleteCookie(response);
        response.sendRedirect("/sign-in");
    }

    private void deleteCookie(HttpServletResponse response) {
        Cookie cookie = sessionService.createCookieForDelete();
        response.addCookie(cookie);
    }

}
