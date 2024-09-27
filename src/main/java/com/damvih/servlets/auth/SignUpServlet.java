package com.damvih.servlets.auth;

import com.damvih.dto.UserRegistrationDto;
import com.damvih.entities.Session;
import com.damvih.entities.User;
import com.damvih.filters.AuthFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/sign-up")
public class SignUpServlet extends BaseAuthServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/sign-up.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                request.getParameter("login"),
                request.getParameter("password"),
                request.getParameter("confirmedPassword")
        );

        User user = userService.save(userRegistrationDto);
        Session createdSession = sessionService.save(user);

        addCookie(response, createdSession);
        response.sendRedirect("/home");
    }

}
