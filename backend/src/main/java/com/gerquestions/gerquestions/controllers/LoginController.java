package com.gerquestions.gerquestions.controllers;

import com.gerquestions.gerquestions.entities.AppUser;
import com.gerquestions.gerquestions.exceptions.UserNotFoundException;
import com.gerquestions.gerquestions.exceptions.UserPasswordIncorrectException;
import com.gerquestions.gerquestions.misc.LoginResponse;
import com.gerquestions.gerquestions.services.AppUserService;
import com.gerquestions.gerquestions.services.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    private AppUserService appUserService;
    private LoginService loginService;

    public LoginController(LoginService loginService, AppUserService appUserService) {
        super();
        this.appUserService = appUserService;
        this.loginService = loginService;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<LoginResponse> authenticate(
            @RequestBody AppUser user
    ) throws UserNotFoundException, UserPasswordIncorrectException {

        AppUser searchedUser = appUserService.searchByEmail(user.getEmail());

        if (!searchedUser.getPassword().equals(user.getPassword())) {
            throw new UserPasswordIncorrectException("Incorrect password.");
        }

        return new ResponseEntity<LoginResponse>(loginService.createToken(searchedUser), HttpStatus.OK);
    }


}
