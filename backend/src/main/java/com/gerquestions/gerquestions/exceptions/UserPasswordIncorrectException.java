package com.gerquestions.gerquestions.exceptions;

public class UserPasswordIncorrectException extends UserException {

    public UserPasswordIncorrectException() {
        super();
    }

    public UserPasswordIncorrectException(String message) {
        super("UserPasswordIncorrectException() -> " + message);
    }
}

