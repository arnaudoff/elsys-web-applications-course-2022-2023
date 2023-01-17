package com.example.twitter.users;

import java.util.Date;

public class User {
    private String username;
    private Date date_registered;
    private int followers_count;
    private int following_count;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate_registered() {
        return date_registered;
    }

    public void setDate_registered(Date date_registered) {
        this.date_registered = date_registered;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(int following_count) {
        this.following_count = following_count;
    }
}
