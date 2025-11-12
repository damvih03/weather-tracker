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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    public static final String SESSION_ID_COOKIE_NAME = "SESSION_ID";

    private final UserService userService;
    private final SessionService sessionService;

    @GetMapping("/sign-in")
    public String getSignInPage() {
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String getSignUpPage() {
        return "sign-up";
    }

    @PostMapping("/sign-in")
    public String signIn(@ModelAttribute UserRequestDto userRequestDto, HttpServletResponse response) {
        UserDto userDto = userService.get(userRequestDto);
        SessionDto sessionDto = sessionService.create(userDto);
        response.addCookie(new Cookie(SESSION_ID_COOKIE_NAME, sessionDto.getId().toString()));
        return "redirect:/";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute UserRegistrationDto userRegistrationDto, HttpServletResponse response) {
        UserDto userDto = userService.create(userRegistrationDto);
        SessionDto sessionDto = sessionService.create(userDto);
        response.addCookie(new Cookie(SESSION_ID_COOKIE_NAME, sessionDto.getId().toString()));
        return "redirect:/";
    }

    @PostMapping("/sign-out")
    public String signOut(@CookieValue(name = SESSION_ID_COOKIE_NAME) String sessionId, HttpServletResponse response) {
        sessionService.delete(UUID.fromString(sessionId));
        response.addCookie(new Cookie(SESSION_ID_COOKIE_NAME, null));
        return "redirect:/sign-in";
    }

}
