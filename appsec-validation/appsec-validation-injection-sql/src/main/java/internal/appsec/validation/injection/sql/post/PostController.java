package internal.appsec.validation.injection.sql.post;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE)
@RestController
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/posts/{slug}")
    public List<Post> getPosts(@PathVariable("slug") String slug) {
        // Imagine that the user is logged in and his id is 2
        Integer currentUserId = 2;
        return postService.getPosts(currentUserId, slug);
    }

}
