package com.damvih.exceptions;

public class DatabaseOperationException extends RuntimeException {

    private static final String MESSAGE = "Something happened with database";

    public DatabaseOperationException() {
        super(MESSAGE);
    }

}