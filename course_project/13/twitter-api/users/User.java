package org.elsysbg.twitterapi.users;

import java.time.LocalDateTime;

public class User {
    private long id;
    private String username;
    private LocalDateTime registrationDate;
    private long followers;
    private long following;
    
    public User(long id, String username, LocalDateTime localDateTime, long followers, long following) {
        this.id = id;
        this.username = username;
        this.registrationDate = localDateTime;
        this.followers = followers;
        this.following = following;
    }

    public long getFollowersCount() {
        return followers;
    }
    
    public long getFollowingCount() {
        return following;
    }
    
    public long getId() {
        return id;
    }
    
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }
    
    public String getUsername() {
        return username;
    }
}
