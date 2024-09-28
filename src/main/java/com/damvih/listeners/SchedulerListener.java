package com.damvih.listeners;

import com.damvih.utils.scheduler.SchedulerUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class SchedulerListener implements ServletContextListener {

    private SchedulerUtil schedulerUtil;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        schedulerUtil = new SchedulerUtil();
        schedulerUtil.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        schedulerUtil.stop();
    }

}
