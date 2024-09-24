package com.damvih.servlets.auth;

import com.damvih.entities.Session;
import com.damvih.filters.AuthFilter;
import com.damvih.utils.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/sign-out")
public class SignOutServlet extends BaseAuthServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = (Session) request.getServletContext().getAttribute(AuthFilter.SESSION_ATTRIBUTE_NAME);
        sessionService.delete(session);
        CookieUtil.deleteCookie(response);
        response.sendRedirect("/sign-in");
    }

}
