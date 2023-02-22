package com.example.dom4;

import java.time.LocalDateTime;

public class Tweets {
    //Each tweet has a unique internal ID, an author (user who tweeted), tweet text and tweet date.
    private  int id;
    private String Author;
    private  String Description;
    private LocalDateTime data;

    public Tweets(int id, String author,String Description) {
        this.id = id;
        Author = author;
        this.data = LocalDateTime.now();
        this.Description = Description;

    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setDateCreated(LocalDateTime date) {
        this.data = date;
    }
}
