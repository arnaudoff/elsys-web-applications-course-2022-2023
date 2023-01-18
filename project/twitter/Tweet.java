package com.project.twitter;

import java.time.LocalDateTime;

public class Tweet {
    private long id;
    private String text;
    private LocalDateTime date;
    private long userId;
    public void setId(long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }



    public Tweet(long id, String text, long userId) {
        this.id = id;
        this.text = text;
        this.date = LocalDateTime.now();
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public long getUserId() {
        return userId;
    }
}
