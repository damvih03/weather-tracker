package com.damvih.services;

import com.damvih.dao.SessionDao;
import com.damvih.dao.UserDao;
import com.damvih.dto.UserRegistrationDto;
import com.damvih.dto.UserRequestDto;
import com.damvih.entities.Session;
import com.damvih.entities.User;
import com.damvih.exceptions.InvalidPasswordException;
import com.damvih.exceptions.UserNotFoundException;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthService {

    private final UserDao userDao;
    private final SessionDao sessionDao;

    public AuthService() {
        userDao = new UserDao();
        sessionDao = new SessionDao();
    }

    public User getUser(UserRequestDto userRequestDto) {
        User user = userDao
                .findByLogin(userRequestDto.getLogin())
                .orElseThrow(UserNotFoundException::new);

        if (!BCrypt.checkpw(userRequestDto.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        return user;
    }

    public User saveUser(UserRegistrationDto userRegistrationDto) {
        User user = User.builder()
                .login(userRegistrationDto.getLogin())
                .password(BCrypt.hashpw(userRegistrationDto.getPassword(), BCrypt.gensalt()))
                .build();
        return userDao.save(user);
    }

    public Session saveSession(User user) {
        Session session = new Session(
                UUID.randomUUID(),
                user,
                LocalDateTime.now().plusHours(6)
        );
        return sessionDao.save(session);
    }

}
