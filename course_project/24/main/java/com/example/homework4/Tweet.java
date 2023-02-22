package com.example.homework4;

import java.util.Date;

public class Tweet {
    private static int counter = 0;
    private int id;
    private String text;
    private Date creationDate;
    private int userId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void assignId() {
        this.id = counter++;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}