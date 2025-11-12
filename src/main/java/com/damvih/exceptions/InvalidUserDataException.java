package com.damvih.exceptions;

public class InvalidUserDataException extends RuntimeException {

    private static final String MESSAGE = "Invalid username or password.";

    public InvalidUserDataException() {
        super(MESSAGE);
    }

}
