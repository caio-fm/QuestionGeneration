package com.gerquestions.gerquestions.exceptions;

public class UserTokenInvalidFormatException extends UserException {

    public UserTokenInvalidFormatException() {
        super();
    }

    public UserTokenInvalidFormatException(String message) {
        super("UserTokenInvalidFormatException() -> " + message);
    }
}