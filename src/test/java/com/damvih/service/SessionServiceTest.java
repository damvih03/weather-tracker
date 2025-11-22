package com.damvih.service;

import com.damvih.config.app.AppConfig;
import com.damvih.config.datasource.DataSourceConfig;
import com.damvih.config.datasource.FlywayConfig;
import com.damvih.config.datasource.HibernateConfig;
import com.damvih.dto.session.SessionDto;
import com.damvih.dto.user.UserDto;
import com.damvih.dto.user.UserRegistrationDto;
import com.damvih.exception.InvalidSessionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, DataSourceConfig.class, FlywayConfig.class, HibernateConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SessionServiceTest {

    private UserDto user;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @BeforeAll
    public void setup() {
        user = userService.create(new UserRegistrationDto(
                "testUserSession",
                "pass123",
                "pass123"
        ));
    }

    @Test
    public void testDeletingActualSession() {
        SessionDto sessionDto = new SessionDto(
                UUID.randomUUID(),
                user,
                LocalDateTime.now().plusHours(6)
        );

        sessionService.create(sessionDto);
        sessionService.deleteIfExpired();

        Assertions.assertDoesNotThrow(() -> sessionService.getValid(sessionDto.getId()));
    }

    @Test
    public void testDeletingExpiredSession() {
        SessionDto sessionDto = new SessionDto(
                UUID.randomUUID(),
                user,
                LocalDateTime.now().minusHours(6)
        );

        sessionService.create(sessionDto);
        sessionService.deleteIfExpired();

        Assertions.assertThrows(InvalidSessionException.class, () -> sessionService.getValid(sessionDto.getId()));
    }

}
