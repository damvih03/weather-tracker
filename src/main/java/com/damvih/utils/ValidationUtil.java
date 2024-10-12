package com.damvih.utils;

import com.damvih.dto.UserRegistrationDto;
import com.damvih.exceptions.RegistrationException;

public class ValidationUtil {

    private static final String LOGIN_PATTERN = "^[a-zA-Z]{4,20}$";
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]{6,20}$";

    public static void validateUserRegistration(UserRegistrationDto userRegistrationDto) {
        String login = userRegistrationDto.getLogin();
        String password = userRegistrationDto.getPassword();
        String confirmedPassword = userRegistrationDto.getConfirmedPassword();

        validateParameterValue(login);
        validateParameterValue(password);
        validateParameterValue(confirmedPassword);

        validateIfPasswordsEquals(password, confirmedPassword);
        validateLogin(login);
        validatePassword(password);
    }

    private static void validateLogin(String login) {
        if (!login.matches(LOGIN_PATTERN)) {
            throw new RegistrationException("Login does not match the pattern!");
        }
    }

    private static void validateIfPasswordsEquals(String password, String confirmedPassword) {
        if (!password.equals(confirmedPassword)) {
            throw new RegistrationException("Entered passwords are not equal!");
        }
    }

    private static void validatePassword(String password) {
        if (!password.matches(PASSWORD_PATTERN)) {
            throw new RegistrationException("Password does not match the pattern!");
        }
    }

    private static void validateParameterValue(String parameterValue) {
        if (parameterValue == null || parameterValue.isBlank()) {
            throw new IllegalArgumentException("Missed parameter!");
        }
    }

    private ValidationUtil() {

    }

}
