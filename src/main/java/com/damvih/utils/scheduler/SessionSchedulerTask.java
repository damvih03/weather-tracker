package com.damvih.utils.scheduler;

import com.damvih.dao.SessionDao;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class SessionSchedulerTask implements Runnable {

    private final SessionDao sessionDao;

    @Override
    public void run() {
        sessionDao.deleteIfExpired(LocalDateTime.now());
    }

}
