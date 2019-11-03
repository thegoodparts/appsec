# appsec-validation-injection-sql

## Context

http://localhost:8080/h2-console/login.jsp?jsessionid=6350ae370854407184e14377bbff4db7

```sql
INSERT INTO user (id, username, email, password) VALUES
  (1, 'admin', 'admin@site.internal', '(;C\b6Wva`9{:LYq'),
  (2, 'user1', 'user1@site.external', '[P:n@dzG?LF"T*2W'),
  (3, 'user2', 'user2@site.external', 'u_3AS8k%cxRN~u~q');
```

```sql
INSERT INTO post (id, slug, title, description) VALUES
  (1, 'post-a', 'Post A', 'Description A'),
  (2, 'post-b', 'Post B', 'Description B'),
  (3, 'post-c', 'Post C', 'Description C');
```

### Retrieve post by user

The following request would retrieve the categories written by 'user1' (id = 2) which have the value 'post-a' as slug:

```bash
curl --request GET \
  --url http://localhost:8080/posts/post-a
```

The application would translate this request into the SQL query that is shown next:

```sql
SELECT id, slug, title, description
FROM posts
WHERE slug = 'post-a'
```

As it can be seen, the result is a simple list with the information of post 'post-a':

![appsec-validation-injection-sql-retrieve-post-by-user.png](README/appsec-validation-injection-sql-retrieve-post-by-user.png)

## Vulnerability

TODO Add screenshot from SonarQube.

```java
    public List<Post> findBySlug(Integer currentUserId, String slug) {
        List<Post> posts = new ArrayList<>();

        // FIXME Use Spring JPA repository or prepareStatement instead of createStatement
        try (Connection connection = DriverManager.getConnection(databaseConfiguration.getUrl(),
                databaseConfiguration.getUser(), databaseConfiguration.getPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT p.id AS id, p.slug AS slug, p.title AS title, p.description AS description" +
                             " FROM posts p, user_posts up" +
                             " WHERE p.id = up.post_id" +
                             " AND up.user_id = ?" +
                             " AND slug = ?"
             )
        ) {
            preparedStatement.setInt(1, currentUserId);
            preparedStatement.setString(2, slug);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(mapToPost(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error("Error reading post {} from database", slug, e);
        }

        return posts;
    }
```

### Retrieve all posts

Since the application was vulnerable to SQL injection, the original query could be altered to retrieve all categories written by any user:

```bash
curl --request GET \
  --url http://localhost:8080/posts/post-a%27%20OR%20%271%27%20=%20%271
```

This request would be translated into the following SQL query:

```sql
SELECT id, slug, title, description
FROM posts
WHERE slug = 'post-a'
  OR '1' = '1'
```

As a result, all categories stored in the database would be returned to the user:

![appsec-validation-injection-sql-retrieve-all-categories.png](README/appsec-validation-injection-sql-retrieve-all-categories.png)

### Retrieve all users

This type of attack could be used to even retrieve sensitive information about the users registered into the application, emails and passwords included:

```bash
curl --request GET \
  --url 'http://localhost:8080/posts/post-a%27%20UNION%20SELECT%20id,%20username,%20email,%20password%20FROM%20users%20WHERE%20%271%27=%20%271'
```

This request would generate a SQL query that would join the `categories` and `users` tables:

```sql
SELECT id, slug, title, description
FROM posts
WHERE slug = 'post-a'
UNION
SELECT id, username, email, password
FROM users
WHERE '1' = '1'
```

As it is shown in the following picture, the attacker would have access to all the credentials stored in the database:

![appsec-validation-injection-sql-retrieve-all-users.png](README/appsec-validation-injection-sql-retrieve-all-users.png)

## Secure code

### Using JpaRepository

```java
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findBySlug(String slug);
}
```

### Using custom queries

```java
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT new Post(id, slug, title, description) FROM Post WHERE slug = ?1")
    List<Post> findBySlug(String slug);
}
```

### Using PreparedStatement

```java
class PostRepository {
    public List<Post> findBySlug(Integer currentUserId, String slug) {
        List<Post> posts = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(databaseConfiguration.getUrl(),
                databaseConfiguration.getUser(), databaseConfiguration.getPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT p.id AS id, p.slug AS slug, p.title AS title, p.description AS description" +
                             " FROM posts p, user_posts up" +
                             " WHERE p.id = up.post_id" +
                             " AND up.user_id = ?" +
                             " AND slug = ?"
             )
        ) {
            preparedStatement.setInt(1, currentUserId);
            preparedStatement.setString(2, slug);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(mapToPost(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error("Error reading post {} from database", slug, e);
        }

        return posts;
    }
}
```
