# appsec-validation-injection-sql

http://localhost:8080/h2-console/login.jsp?jsessionid=6350ae370854407184e14377bbff4db7

```sql
SELECT id, slug, title
FROM posts, user_posts
WHERE posts.id = user_posts.post_id
  AND user_posts.user_id = 2
  AND slug = 'post-a'
```

![appsec-validation-injection-sql-retrieve-post-by-user.png](README/appsec-validation-injection-sql-retrieve-post-by-user.png)

```sql
SELECT id, slug, title
FROM posts, user_posts
WHERE posts.id = user_posts.post_id
  AND user_posts.user_id = 2
  AND slug = 'post-a'
  OR '1' = '1'
```

![appsec-validation-injection-sql-retrieve-all-posts.png](README/appsec-validation-injection-sql-retrieve-all-posts.png)

```sql
SELECT id, slug, title
FROM posts, user_posts
WHERE posts.id = user_posts.post_id
  AND user_posts.user_id = 2
  AND slug = 'post-a'
UNION
SELECT id, username, email, password
FROM users
WHERE '1'= '1'
```

![appsec-validation-injection-sql-retrieve-all-users.png](README/appsec-validation-injection-sql-retrieve-all-users.png)
