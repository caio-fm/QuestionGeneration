package com.gerquestions.gerquestions.exceptions;

public class UserTokenExpired extends UserException {

    public UserTokenExpired() {
        super();
    }

    public UserTokenExpired(String message) {
        super("UserTokenExpired() -> " + message);
    }
}
