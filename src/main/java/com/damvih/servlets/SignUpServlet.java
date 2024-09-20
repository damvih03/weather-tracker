package com.damvih.servlets;

import com.damvih.dto.UserRegistrationDto;
import com.damvih.entities.Session;
import com.damvih.entities.User;
import com.damvih.services.AuthService;
import com.damvih.utils.CookieUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {

    private AuthService authService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        authService = new AuthService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                request.getParameter("login"),
                request.getParameter("password"),
                request.getParameter("confirmedPassword")
        );

        User user = authService.saveUser(userRegistrationDto);
        Session createdSession = authService.saveSession(user);

        CookieUtil.addCookie(response, createdSession);
        response.sendRedirect("index.html");
    }

}
