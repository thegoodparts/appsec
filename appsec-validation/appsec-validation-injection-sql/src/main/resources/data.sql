DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL
);

INSERT INTO user (id, username, email, password) VALUES
  (1, 'admin', 'admin@site.internal', '(;C\b6Wva`9{:LYq'),
  (2, 'user1', 'user1@site.external', '[P:n@dzG?LF"T*2W'),
  (3, 'user2', 'user2@site.external', 'u_3AS8k%cxRN~u~q');

DROP TABLE IF EXISTS post;

CREATE TABLE post (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  slug VARCHAR(250) NOT NULL,
  title VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL
);

INSERT INTO post (id, slug, title, description) VALUES
  (1, 'post-a', 'Post A', 'Description A'),
  (2, 'post-b', 'Post B', 'Description B'),
  (3, 'post-c', 'Post C', 'Description C');
