package internal.appsec.validation.injection.sql.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import internal.appsec.validation.injection.sql.model.User;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<User> findByUsername(String username) {
        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "scott", "tiger");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT username, email FROM users WHERE username = " + username)) {
            if (resultSet.next()) {
                return Optional.of(User.builder()
                        .email(resultSet.getString("email"))
                        .username(resultSet.getString("username"))
                        .build());
            }
        } catch (SQLException e) {
            log.error("Error reading username {} from database", username, e);
        }

        return Optional.empty();
    }

}
