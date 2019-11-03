package internal.appsec.validation.injection.sql.post;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE)
@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Transactional
    public List<Post> getPosts(String postSlug) {
        return postRepository.findBySlug(postSlug);
    }

}
