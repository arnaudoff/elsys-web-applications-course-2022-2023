package com.example.elonmuskhw.DTOs;

import java.util.Date;

public class UserDTO {
    private String username;

    private Date registrationDate;

    private int followersCount;

    private int followingCount;

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

    public UserDTO(String username, Date registrationDate, int followersCount, int followingCount) {
        this.username = username;
        this.registrationDate = registrationDate;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
    }
}
