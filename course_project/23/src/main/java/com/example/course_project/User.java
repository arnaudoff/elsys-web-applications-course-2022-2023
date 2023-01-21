package com.example.course_project;

import java.util.Date;

public class User {
    private long id;
    private String username;
    private Date registrationDate;
    private long followers;
    private long following;

    public User(long id, String username, Date registrationDate, long followers, long following) {
        this.id = id;
        this.username = username;
        this.registrationDate = registrationDate;
        this.followers = followers;
        this.following = following;
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
}
