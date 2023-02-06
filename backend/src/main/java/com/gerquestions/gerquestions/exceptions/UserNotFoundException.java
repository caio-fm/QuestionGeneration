package com.gerquestions.gerquestions.exceptions;

public class UserNotFoundException extends UserException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super("UserNotFoundException() -> " + message);
    }
}