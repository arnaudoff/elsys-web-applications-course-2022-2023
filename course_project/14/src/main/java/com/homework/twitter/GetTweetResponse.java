package com.homework.twitter;

import java.time.LocalDateTime;

public class GetTweetResponse {
    private long id;
    private LocalDateTime date;
    private String text;

    private GetUserResponse author;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public GetUserResponse getAuthor() {
        return author;
    }

    public void setAuthor(GetUserResponse author) {
        this.author = author;
    }
}
