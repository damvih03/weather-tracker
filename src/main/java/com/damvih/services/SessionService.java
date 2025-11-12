package com.damvih.services;

import com.damvih.dao.SessionDao;
import com.damvih.dto.SessionDto;
import com.damvih.dto.UserDto;
import com.damvih.entities.Session;
import com.damvih.entities.User;
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

    public SessionDto create(UserDto userDto) {
        Session session = sessionDao.save(new Session(
                UUID.randomUUID(),
                modelMapper.map(userDto, User.class),
                LocalDateTime.now().plusHours(6)
        ));
        return new SessionDto(session.getId(), modelMapper.map(session.getUser(), UserDto.class));
    }

    public void delete(UUID id) {
        Session session = sessionDao
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        sessionDao.delete(session);
    }

    public SessionDto getValid(UUID id) {
        Session session = sessionDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Session is not found."));
        if (isExpired(session)) {
            throw new RuntimeException("Session is expired.");
        }
        return new SessionDto(session.getId(), modelMapper.map(session.getUser(), UserDto.class));
    }

    public boolean isExpired(Session session) {
        return LocalDateTime.now().isAfter(session.getExpiresAt());
    }

}
