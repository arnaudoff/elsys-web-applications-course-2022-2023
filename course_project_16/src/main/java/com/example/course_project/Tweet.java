package com.example.course_project;

import java.util.Date;

public class Tweet {
    private static long idGen = 0;

    private long id;
    private Date date;
    private long author;
    private String text;

    public Tweet(long author, String text) {
        id = idGen++;
        date = new Date();
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
