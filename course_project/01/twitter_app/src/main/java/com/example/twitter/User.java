package com.example.twitter;

import java.time.LocalDate;
import java.util.Random;

public class User {
    private static int idGenerator = 0;

    private int id;
    private String username;
    private LocalDate registrationDate;
    private int followers;
    private int following;

    public User(String username) {
        this.username = username;
        setId();
        setRegistrationDate();
        setFollowers(new Random().nextInt());
        setFollowing(new Random().nextInt());

    }

    public int getId() {
        return id;
    }

    public void setId() {
        idGenerator ++;
        this.id = idGenerator;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate() {
        this.registrationDate = LocalDate.now();
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }
}
