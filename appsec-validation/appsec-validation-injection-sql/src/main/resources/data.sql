DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL
);

INSERT INTO users (username, email) VALUES
  ('admin', 'admin@site.internal'),
  ('guest', 'guest@site.external');
