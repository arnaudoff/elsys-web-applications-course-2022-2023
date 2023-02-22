package com.homework.twitter;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "user")
@Entity(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "BIGINT", updatable = false, insertable = false)
    private long id;

    @Column(name = "username", nullable = false, columnDefinition = "VARCHAR(255)")
    private String username;

    @Column(name = "registration_date", nullable = false, columnDefinition = "DATETIME", updatable = false)
    private LocalDateTime registrationDate;

    @Column(name = "followers_count", nullable = false, columnDefinition = "BIGINT")
    private long followersCount;

    @Column(name = "following_count", nullable = false, columnDefinition = "BIGINT")
    private long followingCount;

    @OneToMany(mappedBy = "author")
    private List<Tweet> tweets = new ArrayList<Tweet>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public long getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(long followersCount) {
        this.followersCount = followersCount;
    }

    public long getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(long followingCount) {
        this.followingCount = followingCount;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }
}
