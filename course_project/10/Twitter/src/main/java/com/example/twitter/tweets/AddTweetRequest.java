package com.example.twitter.tweets;

import java.util.Date;

public class AddTweetRequest {
    private String text;
    private int user_id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
