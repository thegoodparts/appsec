package internal.appsec.validation.injection.sql.post;

import static java.util.stream.Collectors.toList;
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
    public List<PostDto> getPosts(@PathVariable("slug") String slug) {
        return toPostDto(postService.getPosts(slug));
    }

    private List<PostDto> toPostDto(List<Post> posts) {
        return posts.stream()
                .map(post -> PostDto.builder()
                        .id(post.getId())
                        .slug(post.getSlug())
                        .title(post.getTitle())
                        .description(post.getDescription())
                        .build())
                .collect(toList());
    }

}
