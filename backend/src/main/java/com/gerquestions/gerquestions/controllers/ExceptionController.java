package com.gerquestions.gerquestions.controllers;

import com.gerquestions.gerquestions.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.gerquestions.gerquestions.controllers")
public class ExceptionController {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<String>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UserTokenInvalidFormatException.class, UserPasswordIncorrectException.class, UserTokenExpired.class})
    public ResponseEntity<String> handleUserException(UserException userException) {
        return new ResponseEntity<String>(userException.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserException userException) {
        return new ResponseEntity<String>(userException.getMessage(), HttpStatus.CONFLICT);
    }

}
