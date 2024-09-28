package com.damvih.utils.scheduler;

import com.damvih.dao.SessionDao;

import java.time.LocalDateTime;

public class SessionSchedulerTask implements Runnable {

    private SessionDao sessionDao;

    @Override
    public void run() {
        sessionDao.deleteIfExpired(LocalDateTime.now());
    }

}
