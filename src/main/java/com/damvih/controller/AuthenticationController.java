package com.damvih.controller;

import com.damvih.dto.SessionDto;
import com.damvih.dto.UserRegistrationDto;
import com.damvih.dto.UserRequestDto;
import com.damvih.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    public static final String SESSION_ID_COOKIE_NAME = "SESSION_ID";

    private final AuthenticationService authenticationService;

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
        SessionDto sessionDto = authenticationService.signIn(userRequestDto);
        response.addCookie(new Cookie(SESSION_ID_COOKIE_NAME, sessionDto.getId().toString()));
        return "redirect:/";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute @Valid UserRegistrationDto userRegistrationDto,
                         BindingResult bindingResult, HttpServletResponse response, Model model) {
        if (bindingResult.hasErrors()) {
            addFieldErrorsToModel(bindingResult, model);
            return "sign-up";
        }
        SessionDto sessionDto = authenticationService.signUp(userRegistrationDto);
        response.addCookie(new Cookie(SESSION_ID_COOKIE_NAME, sessionDto.getId().toString()));
        return "redirect:/";
    }

    @PostMapping("/sign-out")
    public String signOut(HttpServletRequest request, HttpServletResponse response) {
        SessionDto sessionDto = (SessionDto) request.getAttribute("session");
        authenticationService.signOut(sessionDto);
        response.addCookie(new Cookie(SESSION_ID_COOKIE_NAME, null));
        return "redirect:/sign-in";
    }

    private void addFieldErrorsToModel(BindingResult bindingResult, Model model) {
        bindingResult.getFieldErrors().forEach(error ->
                model.addAttribute(error.getField() + "Error", error.getDefaultMessage())
        );
    }

}
