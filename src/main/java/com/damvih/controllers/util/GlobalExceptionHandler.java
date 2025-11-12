package com.damvih.controllers.util;

import com.damvih.exceptions.InvalidSessionException;
import com.damvih.exceptions.InvalidUserDataException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidSessionException.class)
    public String handleInvalidSessionException() {
        return "redirect:/sign-in";
    }

    @ExceptionHandler(InvalidUserDataException.class)
    public String handleInvalidUserDataException(Model model) {
        model.addAttribute("error", "Invalid username or password.");
        return "sign-in";
    }

}
