package com.example.course_project;

public class TweetRequest {
    private String text;
    private long authorId;

    public void setText(String text) {
        this.text = text;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getText() {
        return text;
    }

    public long getAuthorId() {
        return authorId;
    }
}
