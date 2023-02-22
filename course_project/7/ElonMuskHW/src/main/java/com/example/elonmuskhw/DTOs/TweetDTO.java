package com.example.elonmuskhw.DTOs;

public class TweetDTO {
    private Integer userId;

    private String text;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TweetDTO(Integer userId, String text) {
        this.userId = userId;
        this.text = text;
    }
}
