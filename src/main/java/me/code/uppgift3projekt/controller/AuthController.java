package me.code.uppgift3projekt.controller;


import lombok.AllArgsConstructor;
import me.code.uppgift3projekt.exception.UserAlreadyExistsException;
import me.code.uppgift3projekt.service.JwtTokenService;
import me.code.uppgift3projekt.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


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
    public String login(@RequestBody Map<String, String> json){
        String username = json.get("username");
        String password = json.get("password");
        var user = userService.loadUserFromCredentials(username, password);
        if (user == null){
            return "";
        }
        return JwtTokenService.createToken(user);
    }

}