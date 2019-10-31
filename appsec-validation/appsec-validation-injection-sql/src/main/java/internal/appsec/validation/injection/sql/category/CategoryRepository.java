package internal.appsec.validation.injection.sql.category;

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
public class CategoryRepository {

    @Autowired
    DatabaseConfiguration databaseConfiguration;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Category> findByPostSlug(String postSlug) {
        List<Category> categories = new ArrayList<>();

        // FIXME Use Spring JPA repository or prepareStatement instead of createStatement
        try (Connection connection = DriverManager.getConnection(databaseConfiguration.getUrl(),
                databaseConfiguration.getUser(), databaseConfiguration.getPassword());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT c.id AS id, c.slug AS slug, c.name AS name" +
                     " FROM categories c, post_categories pc, posts p" +
                     " WHERE c.id = pc.category_id" +
                     " AND p.id = pc.post_id" +
                     " AND p.slug = '" + postSlug + "'")
        ) {
            while (resultSet.next()) {
                categories.add(mapToCategory(resultSet));
            }
        } catch (SQLException e) {
            log.error("Error reading categories for post with slug {} from database", postSlug, e);
        }

        return categories;
    }

    private Category mapToCategory(ResultSet resultSet) throws SQLException {
        return Category.builder()
                .id(resultSet.getInt("id"))
                .slug(resultSet.getString("slug"))
                .name(resultSet.getString("name"))
                .build();
    }

}
