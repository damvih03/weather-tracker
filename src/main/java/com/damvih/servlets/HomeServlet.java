package com.damvih.servlets;

import com.damvih.dao.UserLocationDao;
import com.damvih.entities.Location;
import com.damvih.entities.Session;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/home", ""})
public class HomeServlet extends HttpServlet {

    private UserLocationDao userLocationDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userLocationDao = (UserLocationDao) config
                .getServletContext()
                .getAttribute("UserLocationDao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = ((Session) request.getServletContext().getAttribute("session"));
        List<Location> locations = userLocationDao.findLocationsByUser(session.getUser());
        request.setAttribute("userLocations", locations);
        request.getRequestDispatcher("/WEB-INF/index.html").forward(request, response);
    }

}
