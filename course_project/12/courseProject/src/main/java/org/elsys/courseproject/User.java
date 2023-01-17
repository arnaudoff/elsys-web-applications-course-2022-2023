package org.elsys.courseproject;

import java.util.Date;
import java.util.UUID;

public class User {
    private String Id;

    private String username;

    private Date registrationDate;

    private int followersCount;

    private int followingCount;

    public String getId() {
        return Id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
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

    public User(String username) {
        this.username = username;
        Id = UUID.randomUUID().toString();
        registrationDate = new Date();
        followersCount = 0;
        followingCount = 0;
    }
}
