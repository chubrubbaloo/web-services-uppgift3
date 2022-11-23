package me.code.uppgift3projekt.controller;


import me.code.uppgift3projekt.exception.UserAlreadyExistsException;
import me.code.uppgift3projekt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> json) throws UserAlreadyExistsException {
        String username = json.get("username");
        String password = json.get("password");

        userService.register(username, password);

        return "Registered!";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> json) throws UserAlreadyExistsException {
        String username = json.get("username");
        String password = json.get("password");


        return "Registered!";
    }

}
