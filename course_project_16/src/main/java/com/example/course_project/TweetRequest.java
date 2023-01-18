package com.example.course_project;

public class TweetRequest {
    private String text;
    private long author;


    public String getText() {
        return text;
    }

    public void setAuthor(long author) {
        this.author = author;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getAuthor() {
        return 0;
    }
}
