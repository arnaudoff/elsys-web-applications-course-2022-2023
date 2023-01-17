package com.example.twitter.users;

import java.util.Date;

public class AddUserRequest {
    private String username;
    private Date date_registered;
    private int followers_count;
    private int following_count;

    public String getUsername() {
        return username;
    }
    public Date getDate_registered() {
        return date_registered;
    }

}
