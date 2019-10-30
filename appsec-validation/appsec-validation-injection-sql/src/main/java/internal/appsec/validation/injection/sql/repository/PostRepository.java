package internal.appsec.validation.injection.sql.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import internal.appsec.validation.injection.sql.model.Post;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class PostRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Post> findBySlug(Integer currentUserId, String slug) {
        List<Post> posts = new ArrayList<>();

        // FIXME Use Spring JPA repository or prepareStatement instead of createStatement
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT id, slug, title, description" +
                     " FROM posts, user_posts" +
                     " WHERE posts.id = user_posts.post_id" +
                     " AND user_posts.user_id = " + currentUserId +
                     " AND slug = '" + slug + "'")
        ) {
            while (resultSet.next()) {
                posts.add(mapToPost(resultSet));
            }
        } catch (SQLException e) {
            log.error("Error reading post {} from database", slug, e);
        }

        return posts;
    }

    private Post mapToPost(ResultSet resultSet) throws SQLException {
        return Post.builder()
                .id(resultSet.getInt("id"))
                .slug(resultSet.getString("slug"))
                .title(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .build();
    }

}
