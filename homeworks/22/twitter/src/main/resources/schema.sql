CREATE DATABASE twitter;
USE twitter;

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       created_at DATETIME NOT NULL,
                       followers_count INT DEFAULT 0,
                       following_count INT DEFAULT 0
);
CREATE TABLE tweets (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        author_id BIGINT NOT NULL,
                        text VARCHAR(280) NOT NULL,
                        created_at TIMESTAMP NOT NULL,
                        FOREIGN KEY (author_id) REFERENCES users(id)
);
