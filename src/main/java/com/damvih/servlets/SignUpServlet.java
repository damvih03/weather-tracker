package com.damvih.servlets;

import com.damvih.dao.UserDao;
import com.damvih.dto.UserRegistrationDto;
import com.damvih.entities.Session;
import com.damvih.entities.User;
import com.damvih.utils.MapperUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/sign-up")
public class SignUpServlet extends BaseServlet {

    private UserDao userDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Session> session = getSessionByCookie(request);
        if (session.isPresent()) {
            response.sendRedirect("/home");
            return;
        }
        request.getRequestDispatcher("sign-up.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String confirmedPassword = request.getParameter("confirmedPassword");

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(login, password, confirmedPassword);

        User user = MapperUtil.getInstance().map(userRegistrationDto, User.class);
        user = userDao.save(user);

        Session createdSession = createSession(user);
        addCookie(response, createdSession);
        response.sendRedirect("/home");
    }

}
