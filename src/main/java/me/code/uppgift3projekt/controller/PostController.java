package me.code.uppgift3projekt.controller;

import lombok.AllArgsConstructor;
import me.code.uppgift3projekt.data.Post;
import me.code.uppgift3projekt.data.User;
import me.code.uppgift3projekt.exception.NotOwnerException;
import me.code.uppgift3projekt.exception.PostAlreadyExistsException;
import me.code.uppgift3projekt.exception.PostDoesNotExistException;
import me.code.uppgift3projekt.service.JwtTokenService;
import me.code.uppgift3projekt.service.PostService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;

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

    @PostMapping("/posts")
    public Post createPost(@RequestBody Map<String, String> json, HttpServletRequest request) throws PostAlreadyExistsException {
        String title = json.get("title");
        String content = json.get("content");
        var user = JwtTokenService.getUserFromToken(request.getHeader("authorization").split(" ")[1]);
        return postService.create((User) user, title, content);
    }

    @PutMapping("/posts")
    public Post updatePost (@RequestBody Map<String, String> json, HttpServletRequest request)
            throws NotOwnerException, PostDoesNotExistException
    {
        String title = json.get("title");
        String updatedContent = json.get("updatedContent");
        var user = JwtTokenService.getUserFromToken(request.getHeader("authorization").split(" ")[1]);
        var post = postService.getByTitle(title);
        return postService.edit((User) user, title, updatedContent);
    }

    @DeleteMapping("/posts")
    public Post deletePost(@RequestBody Map<String, String> json, HttpServletRequest request)
            throws NotOwnerException, PostDoesNotExistException
    {
        String title = json.get("title");
        var user = JwtTokenService.getUserFromToken(request.getHeader("authorization").split(" ")[1]);
        return postService.delete((User) user, title);
    }




}
