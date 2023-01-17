package com.example.proektinternet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    private int id;
    private String username;
    private Date registrationDate;
    private int followersCount;
    private int followingCount;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
        this.registrationDate = new Date();
        this.followersCount = (int) Math.floor(Math.random() * 1000);
        this.followingCount = (int) Math.floor(Math.random() * 1000);
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username +
                ", registrationDate=" + registrationDate +
                ", followersCount=" + followersCount +
                ", followingCount=" + followingCount +
                '}';
    }
}