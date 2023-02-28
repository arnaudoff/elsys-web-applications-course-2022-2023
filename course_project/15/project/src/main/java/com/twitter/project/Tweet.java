package com.twitter.project;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Tweet {
    private int authorId;
    private String tweetText;
    private LocalDateTime tweetDate;


    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
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

