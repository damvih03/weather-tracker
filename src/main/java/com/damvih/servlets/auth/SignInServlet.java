package com.damvih.servlets.auth;

import com.damvih.dto.UserRequestDto;
import com.damvih.entities.Session;
import com.damvih.entities.User;
import com.damvih.filters.AuthFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/sign-in")
public class SignInServlet extends BaseAuthServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/sign-in.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRequestDto userRequestDto = new UserRequestDto(
                request.getParameter("login"),
                request.getParameter("password")
        );

        User user = userService.getUser(userRequestDto);
        Session createdSession = sessionService.save(user);

        request.setAttribute(AuthFilter.SESSION_ATTRIBUTE_NAME, createdSession);
        addCookie(response, createdSession);
    }

}
