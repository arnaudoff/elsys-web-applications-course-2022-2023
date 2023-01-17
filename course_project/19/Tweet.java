package com.example.tweet;

import java.util.Date;

public class Tweet {
    private long id;
    private String author;
    private String text;
    private Date date;

    public Tweet(long id, String author, String text, Date date) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}

