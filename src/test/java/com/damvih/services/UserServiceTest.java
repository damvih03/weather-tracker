package com.damvih.services;

import com.damvih.dao.UserDao;
import com.damvih.dto.UserRegistrationDto;
import com.damvih.dto.UserRequestDto;
import com.damvih.exceptions.EntityAlreadyExistsException;
import com.damvih.exceptions.InvalidPasswordException;
import com.damvih.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

    private static UserService userService;
    private static UserDao userDao;

    @BeforeAll
    public static void setup() {
        userDao = new UserDao();
        userService = new UserService(userDao);
    }

    @Test
    public void testCorrectCreation() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "user1",
                "password",
                "password"
        );
        userService.save(userRegistrationDto);

        Assertions.assertTrue(userDao
                .findByLogin(userRegistrationDto.getLogin())
                .isPresent()
        );
    }

    @Test
    public void testCreationWithExistingLogin() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "user2",
                "password",
                "password"
        );
        userService.save(userRegistrationDto);
        Assertions.assertThrows(EntityAlreadyExistsException.class, () -> userService.save(userRegistrationDto));
    }

    @Test
    public void testCorrectGettingUser() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "user3",
                "password",
                "password"
        );
        userService.save(userRegistrationDto);

        UserRequestDto userRequestDto = new UserRequestDto(
                userRegistrationDto.getLogin(),
                userRegistrationDto.getPassword()
        );

        Assertions.assertDoesNotThrow(() -> userService.getUser(userRequestDto));
    }

    @Test
    public void testGettingUserWithWrongLoginAndPassword() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "user4",
                "password",
                "password"
        );
        userService.save(userRegistrationDto);

        UserRequestDto userRequestWithWrongPassword = new UserRequestDto(
                userRegistrationDto.getLogin(),
                "wrongPassword"
        );
        Assertions.assertThrows(InvalidPasswordException.class, () -> userService.getUser(userRequestWithWrongPassword));

        UserRequestDto userRequestWithWrongLogin = new UserRequestDto(
                "wrongLogin",
                userRegistrationDto.getPassword()
        );
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUser(userRequestWithWrongLogin));
    }

}
