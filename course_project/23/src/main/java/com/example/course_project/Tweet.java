package com.example.course_project;

import java.util.Date;

public class Tweet {
    private long id;
    private Date date;
    private long author;
    private String text;

    public Tweet(long id, Date date, long author, String text) {
        this.id = id;
        this.date = date;
        this.author = author;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public long getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
