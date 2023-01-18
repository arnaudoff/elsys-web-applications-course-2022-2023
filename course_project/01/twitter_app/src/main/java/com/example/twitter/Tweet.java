package com.example.twitter;

import java.time.LocalDate;

public class Tweet {
    private static int idGenerator = 0;

    private int id;
    private User author;
    private String text;
    private LocalDate date;

    public Tweet(User author, String text) {
        setId();
        this.author = author;
        this.text = text;
        setDate();
    }

    public int getId() {
        return id;
    }

    public void setId() {
        idGenerator ++;
        this.id = idGenerator;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate() {
        this.date = LocalDate.now();
    }
}
