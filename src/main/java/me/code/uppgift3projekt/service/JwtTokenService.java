package me.code.uppgift3projekt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenService {

    String secret = "secret";
    Algorithm algorithm;
    JWTVerifier verifier;
    UserService userService;

    @Autowired
    public JwtTokenService(UserService userService) {
        this.userService = userService;
        algorithm = Algorithm.HMAC512(secret);
        verifier = JWT.require(algorithm).acceptExpiresAt(0).build();
    }

    public String createToken(UserDetails user){
        if (user == null){
            return null;
        }
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+10000*1000))
                .sign(algorithm);
    }

    public UserDetails getUserFromToken(String jwtToken){
        if (jwtToken == null){
            return null;
        }
        try{
            return userService.loadUserByUsername(verifier.verify(jwtToken).getSubject());
        } catch (JWTVerificationException ignored){}
        return null;
    }

    public boolean validate(String jwtToken) {
        if (jwtToken == null){
            return false;
        }
        try {
            return verifier.verify(jwtToken) != null;
        } catch (JWTVerificationException ignored) {
        }
        return false;
    }
}
