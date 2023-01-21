package com.example.Twitter;

import java.sql.Date;
import java.time.LocalDate;

public class Tweet {

    private static int value = 0;
    private int id;
    private String author;
    private String tweetText;
    private LocalDate tweetDate;

    public Tweet(String author, String tweetText) {
        value++;
        this.id = value;
        this.author = author;
        this.tweetText = tweetText;
        this.tweetDate = java.time.LocalDate.now();
    }

    public Tweet(int id, String author, String tweetText, LocalDate tweetDate) {
        this.id = id;
        this.author = author;
        this.tweetText = tweetText;
        this.tweetDate = tweetDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

    public LocalDate getTweetDate() {
        return tweetDate;
    }

    public void setTweetDate(LocalDate tweetDate) {
        this.tweetDate = tweetDate;
    }
}


