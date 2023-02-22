package com.example.homework4;

public class TweetRequestProcessor {
    private String text;
    private int authorId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}