package com.damvih.service;

import com.damvih.dto.SessionDto;
import com.damvih.dto.UserDto;
import com.damvih.dto.UserRegistrationDto;
import com.damvih.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${session-duration-in-minutes}")
    private int sessionDurationInMinutes;

    private final UserService userService;
    private final SessionService sessionService;

    public SessionDto signIn(UserRequestDto userRequestDto) {
        UserDto userDto = userService.get(userRequestDto);

        SessionDto sessionDto = createActual(userDto);

        sessionService.create(sessionDto);
        return sessionDto;
    }

    public SessionDto signUp(UserRegistrationDto userRegistrationDto) {
        UserDto userDto = userService.create(userRegistrationDto);

        SessionDto sessionDto = createActual(userDto);

        sessionService.create(sessionDto);
        return sessionDto;
    }

    public void signOut(SessionDto sessionDto) {
        sessionService.delete(sessionDto.getId());
    }

    private SessionDto createActual(UserDto userDto) {
        return new SessionDto(
                UUID.randomUUID(),
                userDto,
                LocalDateTime.now().plusMinutes(sessionDurationInMinutes)
        );
    }

}
