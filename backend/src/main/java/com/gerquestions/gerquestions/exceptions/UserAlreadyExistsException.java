package com.gerquestions.gerquestions.exceptions;

public class UserAlreadyExistsException extends UserException {

    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String message) {
        super("UserAlreadyExistException() -> " + message);
    }
}
