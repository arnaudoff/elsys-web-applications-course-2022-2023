package com.example.dom4;

import java.time.LocalDateTime;

public class User {
    //Each user has a unique internal ID, a username, registration date, followers count and following count
    private String UserName;
    private int id;
    private LocalDateTime data;
    private int FollowersCount;
    private int FollowingsCount;

    public User(String userName, int id) {
        UserName = userName;
        this.id = id;
        FollowersCount = 0;
        FollowingsCount = 0;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public int getFollowersCount() {
        return FollowersCount;
    }

    public void setFollowersCount(int followersCount) {
        FollowersCount = followersCount;
    }

    public int getFollowingsCount() {
        return FollowingsCount;
    }

    public void setFollowingsCount(int followingsCount) {
        FollowingsCount = followingsCount;
    }
}
