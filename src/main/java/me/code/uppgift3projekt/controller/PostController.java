package me.code.uppgift3projekt.controller;

import lombok.AllArgsConstructor;
import me.code.uppgift3projekt.service.JwtTokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
public class PostController {

    private final JwtTokenService JwtTokenService;

    @GetMapping("/posts/yo")
    public String yo(HttpServletRequest request){
        var user = JwtTokenService.getUserFromToken(request.getHeader("authorization").split(" ")[1]);
        return "yo " + user.getUsername();
    }

}
