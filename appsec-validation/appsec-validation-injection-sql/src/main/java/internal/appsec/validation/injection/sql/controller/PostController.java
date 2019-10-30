package internal.appsec.validation.injection.sql.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import internal.appsec.validation.injection.sql.model.Post;
import internal.appsec.validation.injection.sql.service.PostService;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts/{slug}")
    List<Post> getPost(@PathVariable("slug") String slug) {
        // Imagine that the user is logged in and his id is 2
        Integer currentUserId = 2;
        return postService.getPost(currentUserId, slug);
    }

}
