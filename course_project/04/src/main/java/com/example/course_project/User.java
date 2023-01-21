package com.example.course_project;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private static long counter = 0;
    private long id;
    private String username;
    private int followers;
    private int following;
    private Date registrationDate;
    private ArrayList<Tweet> tweets;

    public User(String username) {
        this.id = counter++;
        this.username = username;
        this.followers = 0;
        this.following = 0;
        this.registrationDate = new Date();
        this.tweets = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }
}
