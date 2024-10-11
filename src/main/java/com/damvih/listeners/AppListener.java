package com.damvih.listeners;

import com.damvih.dao.LocationDao;
import com.damvih.dao.SessionDao;
import com.damvih.dao.UserDao;
import com.damvih.dao.UserLocationDao;
import com.damvih.services.*;
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

        LocationService locationService = new LocationService(
                (LocationDao) servletContext.getAttribute("LocationDao")
        );
        servletContext.setAttribute("LocationService", locationService);

        UserLocationService userLocationService = new UserLocationService(
                (UserLocationDao) servletContext.getAttribute("UserLocationDao")
        );
        servletContext.setAttribute("UserLocationService", userLocationService);

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

        UserLocationDao userLocationDao = new UserLocationDao();
        servletContext.setAttribute("UserLocationDao", userLocationDao);
    }

}
