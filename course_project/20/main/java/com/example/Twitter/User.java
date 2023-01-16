package com.example.Twitter;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class User {
    private static int value = 0;

    private int id;
    private String username;
    private LocalDate regidtrationDate;
    private int followersCount;
    private int followingCount;
//    private List<Tweet> tweets;

    public User(String username) {
        value++;
        this.id = value;
        this.username = username;
        this.regidtrationDate =  java.time.LocalDate.now();
        this.followersCount = 0;
        this.followingCount = 0;
//        this.tweets = new ArrayList<>();
    }

    public User(int id, String username , LocalDate regidtrationDate, int followersCount, int followingCount) {
        this.id = id;
        this.username = username;
        this.regidtrationDate = regidtrationDate;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
    }

    private String createId(){
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getRegidtrationDate() {
        return regidtrationDate;
    }

    public void setRegidtrationDate(LocalDate regidtrationDate) {
        this.regidtrationDate = regidtrationDate;
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

//    public List<Tweet> getTweets() {
//        return tweets;
//    }
//
//    public void setTweets(List<Tweet> tweets) {
//        this.tweets = tweets;
//    }
}
