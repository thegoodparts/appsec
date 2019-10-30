DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL
);

INSERT INTO users (id, username, email, password) VALUES
  (1, 'admin', 'admin@site.internal', '(;C\b6Wva`9{:LYq'),
  (2, 'user1', 'user1@site.external', '[P:n@dzG?LF"T*2W'),
  (3, 'user2', 'user2@site.external', 'u_3AS8k%cxRN~u~q');

DROP TABLE IF EXISTS posts;

CREATE TABLE posts (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  slug VARCHAR(250) NOT NULL,
  title VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL
);

INSERT INTO posts (id, slug, title, description) VALUES
  (1, 'post-a', 'Post A', 'Description A'),
  (2, 'post-b', 'Post B', 'Description B'),
  (3, 'post-c', 'Post C', 'Description C');

DROP TABLE IF EXISTS user_posts;

CREATE TABLE user_posts (
  user_id INT,
  post_id INT,
  PRIMARY KEY (user_id, post_id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (post_id) REFERENCES posts(id)
);

INSERT INTO user_posts (user_id, post_id) VALUES
  (2, 1),
  (2, 2),
  (3, 3);
