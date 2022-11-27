package me.code.uppgift3projekt.controller;

import lombok.AllArgsConstructor;
import me.code.uppgift3projekt.data.Post;
import me.code.uppgift3projekt.data.PostDTO;
import me.code.uppgift3projekt.data.User;
import me.code.uppgift3projekt.exception.*;
import me.code.uppgift3projekt.service.JwtTokenService;
import me.code.uppgift3projekt.service.PostService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

@RestController
@AllArgsConstructor
public class PostController {


    private final JwtTokenService JwtTokenService;

    private final PostService postService;


    @GetMapping("/posts/getAll")
    public Collection<PostDTO> getPosts() {
        return postService.getAll().stream().map(Post::toDTO).toList();
    }

    @PostMapping("/posts")
    public PostDTO createPost(@RequestBody Map<String, String> json, HttpServletRequest request) throws PostAlreadyExistsException, NullContentException, NullTitleException, NullUserException {
        String title = json.get("title");
        String content = json.get("content");
        var user = getUserFromRequest(request);
        return postService.create(user, title, content).toDTO();
    }

    @PutMapping("/posts")
    public PostDTO updatePost(@RequestBody Map<String, String> json, HttpServletRequest request)
            throws NotOwnerException, PostDoesNotExistException, NullContentException, NullTitleException, NullUserException {
        String title = json.get("title");
        String updatedContent = json.get("updatedContent");
        var user = getUserFromRequest(request);
        return postService.edit(user, title, updatedContent).toDTO();
    }

    @DeleteMapping("/posts")
    public PostDTO deletePost(@RequestBody Map<String, String> json, HttpServletRequest request)
            throws NotOwnerException, PostDoesNotExistException, NullUserException {
        String title = json.get("title");
        var user = getUserFromRequest(request);
        return postService.delete(user, title).toDTO();
    }

    @ExceptionHandler(PostDoesNotExistException.class)
    public String postDoesNotExistExceptionHandler(HttpServletResponse response) {
        response.setStatus(404);
        return "Requested title does not exist.";
    }

    @ExceptionHandler(PostAlreadyExistsException.class)
    public String postAlreadyExistsExceptionHandler(HttpServletResponse response) {
        response.setStatus(409);
        return "Title already exists. Pick a different title.";
    }

    @ExceptionHandler(NotOwnerException.class)
    public String notOwnerExceptionHandler(HttpServletResponse response) {
        response.setStatus(403);
        return "Permission denied. Wrong credentials for specific resource.";
    }

    @ExceptionHandler(NullTitleException.class)
    public String nullTitleExceptionHandler(HttpServletResponse response) {
        response.setStatus(406);
        return "Request must contain title.";
    }

    @ExceptionHandler(NullContentException.class)
    public String nullContentExceptionHandler(HttpServletResponse response) {
        response.setStatus(406);
        return "Request must contain content.";
    }

    @ExceptionHandler(NullUserException.class)
    public String nullUserExceptionHandler(HttpServletResponse response) {
        response.setStatus(401);
        return "No user found, please reauthenticate";
    }

    public User getUserFromRequest(HttpServletRequest request) throws NullUserException {
        return JwtTokenService.getUserFromToken(request.getHeader("authorization").split(" ")[1]);
    }

}
