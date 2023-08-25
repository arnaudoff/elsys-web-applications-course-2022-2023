package com.example.proektinternet;

import java.util.Date;

public class Tweet {
    private int id;
    private User author;
    private String tweetText;
    private Date tweetDate;

    public Tweet(int id, User author, String tweetText) {
        this.id = id;
        this.author = author;
        this.tweetText = tweetText;
        this.tweetDate = new Date();
    }

    public int getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public String getTweetText() {
        return tweetText;
    }

    public Date getTweetDate() {
        return tweetDate;
    }
    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }


    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", author=" + author +
                ", tweetText='" + tweetText + '\'' +
                ", tweetDate=" + tweetDate +
                '}';
    }
}