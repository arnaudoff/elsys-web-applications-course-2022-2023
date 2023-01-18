package com.twitter.twitterapplication;

import java.util.Date;

public class Tweet {
    private int id;
    private String text;
    private Date dateCreated;
    private int authorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
