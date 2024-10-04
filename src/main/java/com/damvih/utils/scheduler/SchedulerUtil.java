package com.damvih.utils.scheduler;

import com.damvih.dao.SessionDao;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerUtil {

    private final ScheduledExecutorService scheduler;
    private static final int CORE_POOL_SIZE = 1;

    public SchedulerUtil() {
        scheduler = Executors.newScheduledThreadPool(CORE_POOL_SIZE);
    }

    public void start() {
        scheduler.scheduleAtFixedRate(new SessionSchedulerTask(new SessionDao()), 0, 1, TimeUnit.HOURS);
    }

    public void stop() {
        scheduler.shutdown();
    }

}
