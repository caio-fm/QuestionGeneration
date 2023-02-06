package com.gerquestions.gerquestions.misc;

import com.gerquestions.gerquestions.entities.AppUser;

import java.util.Date;

public class LoginResponse {

    public String token;
    public String name;

    public String identifier;
    public long expires_in;

    public LoginResponse(String token, String name, long expires_in) {
        super();
        this.token = token;
        this.name = name;
        this.expires_in = expires_in;
    }

}
