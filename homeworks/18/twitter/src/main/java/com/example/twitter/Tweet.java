package com.example.twitter;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User author;

    private String tweetText;

    private LocalDateTime tweetDate;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

    public LocalDateTime getTweetDate() {
        return tweetDate;
    }

    public void setTweetDate(LocalDateTime tweetDate) {
        this.tweetDate = tweetDate;
    }

}