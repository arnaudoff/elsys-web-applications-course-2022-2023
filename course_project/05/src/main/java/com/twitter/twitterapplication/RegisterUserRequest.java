package com.twitter.twitterapplication;

import java.util.Date;

public class RegisterUserRequest {
    private String username;
    private Date dateRegistered;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }
}
