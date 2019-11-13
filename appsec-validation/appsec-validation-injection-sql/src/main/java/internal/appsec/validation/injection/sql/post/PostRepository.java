package internal.appsec.validation.injection.sql.post;

import static lombok.AccessLevel.PRIVATE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import internal.appsec.validation.injection.sql.db.DatabaseConfiguration;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@FieldDefaults(level = PRIVATE)
@Repository
@Slf4j
class PostRepository {

    @Autowired
    DatabaseConfiguration databaseConfiguration;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Post> findBySlug(String slug) {
        List<Post> posts = new ArrayList<>();

        // FIXME Use Spring JPA repository or prepareStatement instead of createStatement
        try (Connection connection = DriverManager.getConnection(databaseConfiguration.getUrl(),
                databaseConfiguration.getUser(), databaseConfiguration.getPassword());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT p.id AS id, p.slug AS slug, p.title AS title, p.description AS description" +
                             " FROM posts p" +
                             " WHERE slug = '" + slug + "'")
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
