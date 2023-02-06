package com.gerquestions.gerquestions.services;

import com.gerquestions.gerquestions.entities.AppUser;
import com.gerquestions.gerquestions.exceptions.UserException;
import com.gerquestions.gerquestions.exceptions.UserTokenInvalidFormatException;
import com.gerquestions.gerquestions.misc.LoginResponse;
import com.gerquestions.gerquestions.misc.TokenFilter;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@Service
public class LoginService {

    private AppUserService appUserService;

    public LoginService(AppUserService appUserService) {
        super();
        this.appUserService = appUserService;
    }

    public void userExists(String authorizationHeader) throws UserException {
        String subject = getUserDoToken(authorizationHeader);
        AppUser user = appUserService.searchByEmail(subject);
    }

    public String getUserDoToken(String authorizationHeader) throws UserException {
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            throw new UserTokenInvalidFormatException("Token has invalid format");

        String token = authorizationHeader.substring(TokenFilter.TOKEN_INDEX);
        String subject = null;

        try {
            subject = Jwts.parser().setSigningKey("655468576D5A7134743777217A25432A462D4A614E645267556A586E3272357538782F413F4428472B4B6250655368566D597033733676397924422645294840").parseClaimsJws(token).getBody().getSubject();
            appUserService.searchByEmail(subject);
        } catch (Exception err) {
            throw new UserTokenInvalidFormatException("Token has invalid format");
        }

        return subject;
    }

    public LoginResponse createToken(AppUser user) {

        Date date = new Date();
        long unixTime = date.getTime() / 1000L;
        long timeToken = 3600;
        String token = Jwts.builder().setSubject(user.getEmail()).signWith(SignatureAlgorithm.HS512, "655468576D5A7134743777217A25432A462D4A614E645267556A586E3272357538782F413F4428472B4B6250655368566D597033733676397924422645294840").setExpiration(new Date(System.currentTimeMillis() + (timeToken * 1000))).compact();
        return new LoginResponse(token, user.getName(),unixTime + timeToken);
    }

}
