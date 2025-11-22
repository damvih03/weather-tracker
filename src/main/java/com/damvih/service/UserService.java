package com.damvih.service;

import com.damvih.dao.UserDao;
import com.damvih.dto.user.UserDto;
import com.damvih.dto.user.UserRegistrationDto;
import com.damvih.dto.user.UserRequestDto;
import com.damvih.entity.User;
import com.damvih.exception.InvalidUserDataException;
import com.password4j.Hash;
import com.password4j.Password;
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
        Hash hashedPassword = Password.hash(userRegistrationDto.getPassword()).withBcrypt();

        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setPassword(hashedPassword.getResult());

        userDao.save(user);

        return modelMapper.map(user, UserDto.class);
    }

    public UserDto get(UserRequestDto userRequestDto) {
        User user = userDao
                .findByUsername(userRequestDto.getUsername())
                .orElseThrow(InvalidUserDataException::new);

        if (!isPasswordCorrect(userRequestDto, user)) {
            throw new InvalidUserDataException();
        }

        return modelMapper.map(user, UserDto.class);
    }

    private boolean isPasswordCorrect(UserRequestDto userRequestDto, User user) {
        return Password.check(userRequestDto.getPassword(), user.getPassword()).withBcrypt();
    }

}
