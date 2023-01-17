package com.example.homework4;

import java.util.Date;

public class User {
    private int id;
    private static int counter = 0;
    private String username;
    private Date dateRegistered;
    private int followersCount;
    private int followingCount;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }
    public void assignId(){
        this.id = counter++;
    }
    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }
}