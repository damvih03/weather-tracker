package com.damvih;

import com.damvih.dao.SessionDao;
import com.damvih.dao.UserDao;
import com.damvih.utils.PersistenceUtil;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    private EntityManagerFactory entityManagerFactory;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        entityManagerFactory = PersistenceUtil.getInstance();

        createServiceObjects(servletContext);
        createDaoObjects(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    private void createServiceObjects(ServletContext servletContext) {

    }

    private void createDaoObjects(ServletContext servletContext) {
        UserDao userDao = new UserDao();
        servletContext.setAttribute("UserDao", userDao);

        SessionDao sessionDao = new SessionDao();
        servletContext.setAttribute("SessionDao", sessionDao);
    }

}
