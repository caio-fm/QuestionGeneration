package com.gerquestions.gerquestions.controllers;

import com.gerquestions.gerquestions.dto.AppUserDTO;
import com.gerquestions.gerquestions.dto.GeneratedQuestionsDTO;
import com.gerquestions.gerquestions.entities.AppUser;
import com.gerquestions.gerquestions.exceptions.UserAlreadyExistsException;
import com.gerquestions.gerquestions.services.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class AppUserController {

    private AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        super();
        this.appUserService = appUserService;
    }


    @PostMapping(value = "/users", consumes = "application/json")
    public ResponseEntity<?> addUser(@Valid @RequestBody AppUser appUser) throws UserAlreadyExistsException {

        appUserService.addAppUser(appUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/users", consumes = "application/json")
    public ResponseEntity<?> getUsers() {

        List<AppUserDTO> usersDTO = appUserService.getUsers();

        return new ResponseEntity<List<AppUserDTO>>(usersDTO, HttpStatus.OK);
    }


}
