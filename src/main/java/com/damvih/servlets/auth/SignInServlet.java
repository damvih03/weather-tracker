package com.damvih.servlets.auth;

import com.damvih.dto.UserRequestDto;
import com.damvih.entities.Session;
import com.damvih.entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/sign-in")
public class SignInServlet extends AuthServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        templateEngine.process("sign-in", webContext, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRequestDto userRequestDto = new UserRequestDto(
                request.getParameter("login"),
                request.getParameter("password")
        );

        User user = userService.getUser(userRequestDto);
        Session createdSession = sessionService.save(user);

        addCookie(response, createdSession);
        response.sendRedirect("/home");
    }

}
