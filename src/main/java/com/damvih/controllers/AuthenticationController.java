package com.damvih.controllers;

import com.damvih.dto.SessionDto;
import com.damvih.dto.UserDto;
import com.damvih.dto.UserRegistrationDto;
import com.damvih.dto.UserRequestDto;
import com.damvih.services.SessionService;
import com.damvih.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    public static final String SESSION_ID_COOKIE_NAME = "SESSION_ID";

    private final UserService userService;
    private final SessionService sessionService;

    @PostMapping("/sign-in")
    public void signIn(@ModelAttribute UserRequestDto userRequestDto, HttpServletResponse response) {
        UserDto userDto = userService.get(userRequestDto);
        SessionDto sessionDto = sessionService.create(userDto);
        response.addCookie(new Cookie(SESSION_ID_COOKIE_NAME, sessionDto.getId().toString()));
    }

    @PostMapping("/sign-up")
    public void signUp(@ModelAttribute UserRegistrationDto userRegistrationDto, HttpServletResponse response) {
        UserDto userDto = userService.create(userRegistrationDto);
        SessionDto sessionDto = sessionService.create(userDto);
        response.addCookie(new Cookie(SESSION_ID_COOKIE_NAME, sessionDto.getId().toString()));
    }

    @PostMapping("/sign-out")
    public void signOut(@CookieValue(name = SESSION_ID_COOKIE_NAME) String sessionId, HttpServletResponse response) {
        sessionService.delete(UUID.fromString(sessionId));
        response.addCookie(new Cookie(SESSION_ID_COOKIE_NAME, null));
    }

}
