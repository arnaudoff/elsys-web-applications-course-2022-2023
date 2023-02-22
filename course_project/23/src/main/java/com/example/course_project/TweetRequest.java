package com.example.course_project;

public class TweetRequest {
    private long author;
    private String text;

    public long getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public void setAuthor(long author) {
        this.author = author;
    }

    public void setText(String text) {
        this.text = text;
    }
}
