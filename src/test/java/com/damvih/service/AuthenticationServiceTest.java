package com.damvih.service;

import com.damvih.config.app.AppConfig;
import com.damvih.config.datasource.DataSourceConfig;
import com.damvih.config.datasource.FlywayConfig;
import com.damvih.config.datasource.HibernateConfig;
import com.damvih.dto.session.SessionDto;
import com.damvih.dto.user.UserRegistrationDto;
import com.damvih.dto.user.UserRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, DataSourceConfig.class, FlywayConfig.class, HibernateConfig.class})
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Test
    public void testCreatingUserWithExistingUsername() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "user1",
                "password1",
                "password1"
        );
        authenticationService.signUp(userRegistrationDto);

        DataIntegrityViolationException exception = Assertions.assertThrows(
                DataIntegrityViolationException.class,
                () -> authenticationService.signUp(userRegistrationDto)
        );
        Assertions.assertTrue(exception.getMessage().contains("users_username_key"));
    }

    @Test
    public void testCorrectCreatingUser() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "user2",
                "password2",
                "password2"
        );

        SessionDto sessionDto = authenticationService.signUp(userRegistrationDto);

        Assertions.assertDoesNotThrow(() -> userService.get(
                new UserRequestDto(
                        userRegistrationDto.getUsername(),
                        userRegistrationDto.getPassword()
                ))
        );
        Assertions.assertDoesNotThrow(() -> sessionService.getValid(sessionDto.getId()));
    }

}
