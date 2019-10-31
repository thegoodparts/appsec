package internal.appsec.validation.injection.sql.post;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE)
@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public List<Post> getPosts(Integer currentUserId, String slug) {
        return postRepository.findBySlug(currentUserId, slug);
    }

}
