package com.damvih.services;

import com.damvih.dao.UserDao;
import com.damvih.dto.UserRegistrationDto;
import com.damvih.dto.UserRequestDto;
import com.damvih.entities.User;
import com.damvih.exceptions.InvalidPasswordException;
import com.damvih.exceptions.UserNotFoundException;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    private final UserDao userDao;

    public UserService() {
        userDao = new UserDao();
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

    public User save(UserRegistrationDto userRegistrationDto) {
        User user = User.builder()
                .login(userRegistrationDto.getLogin())
                .password(BCrypt.hashpw(userRegistrationDto.getPassword(), BCrypt.gensalt()))
                .build();
        return userDao.save(user);
    }

}
