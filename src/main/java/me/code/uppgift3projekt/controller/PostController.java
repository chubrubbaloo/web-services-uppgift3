package me.code.uppgift3projekt.controller;

import lombok.AllArgsConstructor;
import me.code.uppgift3projekt.data.Post;
import me.code.uppgift3projekt.service.JwtTokenService;
import me.code.uppgift3projekt.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@AllArgsConstructor
public class PostController {


    private final JwtTokenService JwtTokenService;

    private final PostService postService;

    @GetMapping("/posts/yo")
    public String yo(HttpServletRequest request){
        var user = JwtTokenService.getUserFromToken(request.getHeader("authorization").split(" ")[1]);
        return "yo " + user.getUsername();
    }

    @GetMapping("/posts")
    public Collection<Post> getPosts() {
        return postService.getAll();
    }

}
