package me.code.uppgift3projekt.controller;


import lombok.AllArgsConstructor;
import me.code.uppgift3projekt.exception.UserAlreadyExistsException;
import me.code.uppgift3projekt.service.JwtTokenService;
import me.code.uppgift3projekt.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    private final JwtTokenService JwtTokenService;


    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> json) throws UserAlreadyExistsException {
        String username = json.get("username");
        String password = json.get("password");

        userService.register(username, password);

        return "Registered!";
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> json){
        String username = json.get("username");
        String password = json.get("password");
        var user = userService.loadUserFromCredentials(username, password);

        var response = new HashMap<String, Object>();
        response.put("user", user.toDTO());
        response.put("token", JwtTokenService.createToken(user));

        return response;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String userAlreadyExistsExceptionHandler(HttpServletResponse response) {
        response.setStatus(409);
        return "A user with that username already exists. Pick a different username.";
    }

}