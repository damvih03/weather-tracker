package com.damvih.services;

import com.damvih.dao.UserDao;
import com.damvih.dto.UserDto;
import com.damvih.dto.UserRegistrationDto;
import com.damvih.dto.UserRequestDto;
import com.damvih.entities.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final ModelMapper modelMapper;

    public UserDto create(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setPassword(userRegistrationDto.getPassword());

        userDao.save(user);

        return modelMapper.map(user, UserDto.class);
    }

    public UserDto get(UserRequestDto userRequestDto) {
        User user = userDao
                .findByUsername(userRequestDto.getUsername())
                .orElseThrow();

        if (!isPasswordCorrect(userRequestDto, user)) {
            throw new RuntimeException("Incorrect password.");
        }

        return modelMapper.map(user, UserDto.class);
    }

    private boolean isPasswordCorrect(UserRequestDto userRequestDto, User user) {
        return userRequestDto.getPassword().equals(user.getPassword());
    }

}
