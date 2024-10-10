package com.damvih.listeners;

import com.damvih.utils.ThymeleafUtil;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.ITemplateEngine;

@WebListener
public class ThymeleafListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ITemplateEngine templateEngine = ThymeleafUtil.buildTemplateEngine(servletContext);
        servletContext.setAttribute("templateEngine", templateEngine);
    }

}
