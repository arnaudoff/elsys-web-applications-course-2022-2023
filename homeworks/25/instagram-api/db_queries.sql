CREATE DATABASE instagram_api;
USE instagram_api;

CREATE TABLE users(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    username VARCHAR(20) UNIQUE NOT NULL,
    password VARCHAR(30) NOT NULL
);

CREATE TABLE posts(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    user_id INT NOT NULL,
    picture VARCHAR(100) NOT NULL,
    description VARCHAR(200),
    
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE post_comments(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	post_id INT NOT NULL,
    user_id INT NOT NULL,
    comment VARCHAR(200) NOT NULL,
    
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE post_likes(
	post_id INT NOT NULL,
    user_id INT NOT NULL,
    
    PRIMARY KEY (post_id, user_id),
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);



-- For testing --

TRUNCATE TABLE posts;
DROP TABLE posts;

SELECT * FROM users;
SELECT * FROM posts;
SELECT * FROM post_comments;
SELECT * FROM post_likes;
SELECT u.password FROM users u WHERE u.username = 'yasaka';
SELECT pc.user_id FROM post_comments pc WHERE pc.post_id = 1;
UPDATE post_comments SET comment = "test" WHERE id = 1;
SELECT * FROM post_comments WHERE post_id = 2;
DROP DATABASE instagram_api;
