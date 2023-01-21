create database instagram;
use instagram;

create table account(
    id bigint auto_increment,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    username varchar(50) not null primary key,
    password varchar(50) not null,
    index(id)
);

create table post(
    id bigint primary key auto_increment,
    description varchar(500) ,
    poster_username varchar(50),
    likes integer default 0,
    foreign key (poster_username) references account(username)
);

create table comment(
    id bigint primary key auto_increment,
    comment varchar(250) not null,
    username varchar(50),
    post_id bigint,
    foreign key (username) references account(username),
    foreign key (post_id) references post(id)
);

create table `like`(
    id bigint primary key auto_increment,
    username varchar(50),
    post_id bigint,
    foreign key (username) references account(username),
    foreign key (post_id) references post(id),
    constraint unique (username, post_id)
)

