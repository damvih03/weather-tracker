package com.damvih.servlets;

import com.damvih.entities.Session;
import com.damvih.exceptions.SessionNotFoundException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/sign-out")
public class SignOutServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = getSessionByCookie(request).orElseThrow(SessionNotFoundException::new);
        getSessionDao().delete(session);
        deleteCookie(response);
        response.sendRedirect("/sign-in");
    }

    private void deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(SignInServlet.SESSION_ID_COOKIE_NAME, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
