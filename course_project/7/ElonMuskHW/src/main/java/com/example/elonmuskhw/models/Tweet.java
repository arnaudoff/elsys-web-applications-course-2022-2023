package com.example.elonmuskhw.models;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "tweets")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "text")
    String text;

    @Column(name = "tweetDate")
    Date tweetDate;

    @ManyToOne
    private User user;

    public Tweet() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTweetDate() {
        return tweetDate;
    }

    public void setTweetDate(Date tweetDate) {
        this.tweetDate = tweetDate;
    }

    public Tweet(User author, String text) {
        this.user = author;
        this.text = text;
        tweetDate = new Date();
    }
}
