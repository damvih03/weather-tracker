package com.damvih.servlets;

import com.damvih.dao.UserDao;
import com.damvih.entities.Session;
import com.damvih.entities.User;
import com.damvih.exceptions.InvalidPasswordException;
import com.damvih.exceptions.UserNotFoundException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/sign-in")
public class SignInServlet extends BaseServlet {

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
        request.getRequestDispatcher("sign-in.html").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Session> session = getSessionByCookie(request);
        if (session.isPresent()) {
            response.sendRedirect("/home");
            return;
        }

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = userDao.findByLogin(login).orElseThrow(UserNotFoundException::new);

        if (!isPasswordCorrect(user, password)) {
            throw new InvalidPasswordException();
        }

        getSessionDao().deleteByUser(user);
        Session createdSession = createSession(user);
        addCookie(response, createdSession);
    }

    private boolean isPasswordCorrect(User user, String password) {
        return user.getPassword().equals(password);
    }

}
