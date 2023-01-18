package com.example.course_project;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private static long idGen = 0;

    private long id;
    private String username;
    private Date registrationDate;
    private long followers;
    private long following;
    private ArrayList<Tweet> tweets;

    public User(String username) {
        id = idGen++;
        this.username = username;
        registrationDate = new Date();
        followers = 0;
        following = 0;
        tweets = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public long getFollowers() {
        return followers;
    }

    public long getFollowing() {
        return following;
    }

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }
}
