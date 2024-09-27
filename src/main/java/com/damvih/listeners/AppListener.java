package com.damvih.listeners;

import com.damvih.dao.LocationDao;
import com.damvih.dao.SessionDao;
import com.damvih.dao.UserDao;
import com.damvih.services.SessionService;
import com.damvih.services.UserService;
import com.damvih.services.WeatherApiService;
import com.damvih.utils.PersistenceUtil;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppListener implements ServletContextListener {

    private EntityManagerFactory entityManagerFactory;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        entityManagerFactory = PersistenceUtil.getInstance();

        createDaoObjects(servletContext);
        createServiceObjects(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    private void createServiceObjects(ServletContext servletContext) {
        UserService userService = new UserService(
                (UserDao) servletContext.getAttribute("UserDao")
        );
        servletContext.setAttribute("UserService", userService);

        SessionService sessionService = new SessionService(
                (SessionDao) servletContext.getAttribute("SessionDao")
        );
        servletContext.setAttribute("SessionService", sessionService);

        WeatherApiService weatherApiService = new WeatherApiService();
        servletContext.setAttribute("WeatherApiService", weatherApiService);
    }

    private void createDaoObjects(ServletContext servletContext) {
        UserDao userDao = new UserDao();
        servletContext.setAttribute("UserDao", userDao);

        SessionDao sessionDao = new SessionDao();
        servletContext.setAttribute("SessionDao", sessionDao);

        LocationDao locationDao = new LocationDao();
        servletContext.setAttribute("LocationDao", locationDao);
    }

}
