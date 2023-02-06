package com.gerquestions.gerquestions.exceptions;

public class UserException extends Exception {

    public UserException() {
        super();
    }

    public UserException(String message) {
        super("throw: UserException(): \n - " + message);
    }
}
