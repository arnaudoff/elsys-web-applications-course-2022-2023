package com.example.twitter;

public class TweetRequest {
    private User author;
    private String text;

    public User getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setText(String text) {
        this.text = text;
    }
}
