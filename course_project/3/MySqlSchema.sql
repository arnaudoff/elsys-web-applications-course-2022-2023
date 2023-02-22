use twitter;

create table users(
	id bigint not null unique auto_increment primary key,
    username varchar(256) not null unique,
    registration_date date not null,
    followers_count int not null,
    following_count int not null
); 
CREATE TABLE tweets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_id BIGINT NOT NULL,
    text VARCHAR(255) NOT NULL,
    tweet_date TIMESTAMP NOT NULL,
    FOREIGN KEY (author_id) REFERENCES users(id)
);
