package com.damvih.services;

import com.damvih.dao.SessionDao;
import com.damvih.dto.SessionDto;
import com.damvih.entities.Session;
import com.damvih.exceptions.InvalidSessionException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionService {

    private final SessionDao sessionDao;
    private final ModelMapper modelMapper;

    public SessionDto create(SessionDto sessionDto) {
        Session session = sessionDao.save(modelMapper.map(sessionDto, Session.class));
        return modelMapper.map(session, SessionDto.class);
    }

    public void delete(UUID id) {
        Session session = sessionDao
                .findById(id)
                .orElseThrow(() -> new InvalidSessionException("Session (" + id + ") is not found."));
        sessionDao.delete(session);
    }

    public SessionDto getValid(UUID id) {
        Session session = sessionDao.findById(id)
                .orElseThrow(() -> new InvalidSessionException("Session (" + id + ") is not found."));
        if (isExpired(session)) {
            throw new InvalidSessionException("Session is expired.");
        }
        return modelMapper.map(session, SessionDto.class);
    }

    public void deleteIfExpired() {
        sessionDao.deleteIfExpired();
    }

    public boolean isExpired(Session session) {
        return LocalDateTime.now().isAfter(session.getExpiresAt());
    }

}
