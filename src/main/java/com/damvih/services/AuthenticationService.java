package com.damvih.services;

import com.damvih.dto.SessionDto;
import com.damvih.dto.UserDto;
import com.damvih.dto.UserRegistrationDto;
import com.damvih.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final SessionService sessionService;

    public SessionDto signIn(UserRequestDto userRequestDto) {
        UserDto userDto = userService.get(userRequestDto);

        SessionDto sessionDto = new SessionDto(
                UUID.randomUUID(),
                userDto,
                LocalDateTime.now().plusHours(6)
        );

        sessionService.create(sessionDto);
        return sessionDto;
    }

    public SessionDto signUp(UserRegistrationDto userRegistrationDto) {
        UserDto userDto = userService.create(userRegistrationDto);

        SessionDto sessionDto = new SessionDto(
                UUID.randomUUID(),
                userDto,
                LocalDateTime.now().plusHours(6)
        );

        sessionService.create(sessionDto);
        return sessionDto;
    }

    public void signOut(SessionDto sessionDto) {
        sessionService.delete(sessionDto.getId());
    }

}
