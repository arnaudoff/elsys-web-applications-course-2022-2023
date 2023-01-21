package com.example.course_project;

import java.util.Date;

public class Tweet {
    private static long counter = 0;

    private long id;
    private long author;
    private String text;
    private Date date;

    public Tweet(long author, String text) {
        this.id = counter++;
        this.author = author;
        this.text = text;
        this.date = new Date();
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public long getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }
}
