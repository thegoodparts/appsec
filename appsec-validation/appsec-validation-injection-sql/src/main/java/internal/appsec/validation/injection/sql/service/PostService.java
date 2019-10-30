package internal.appsec.validation.injection.sql.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import internal.appsec.validation.injection.sql.model.Post;
import internal.appsec.validation.injection.sql.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getPost(Integer currentUserId, String slug) {
        return postRepository.findBySlug(currentUserId, slug);
    }

}
