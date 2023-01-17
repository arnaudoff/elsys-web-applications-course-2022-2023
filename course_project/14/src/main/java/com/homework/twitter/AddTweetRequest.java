package com.homework.twitter;

public class AddTweetRequest {
    private long authorId;
    private String text;

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthor(long authorId) {
        this.authorId = authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
