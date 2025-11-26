package com.damvih.controller.util;

import com.damvih.exception.InvalidSessionException;
import com.damvih.exception.InvalidUserDataException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.hibernate.exception.ConstraintViolationException.*;

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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolationException(DataIntegrityViolationException exception, Model model) {
        ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception.getCause();
        if (constraintViolationException.getKind().equals(ConstraintKind.UNIQUE)) {
            model.addAttribute("error", "Username is already taken. Try another one.");
            return "sign-up";
        }
        return "error";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingServletRequestParameterException() {
        return "redirect:/";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException() {
        return "redirect:/";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception) {
        System.out.println(exception.getMessage());
        return "error";
    }

}
