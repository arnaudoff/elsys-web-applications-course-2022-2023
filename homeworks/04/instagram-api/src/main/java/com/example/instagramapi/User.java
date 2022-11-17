package com.example.instagramapi;

import java.util.ArrayList;

public class User {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private ArrayList<Post> posts;

    public User(String firstname, String lastname, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.posts = new ArrayList<>();
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void addPost (Post newPost){
        posts.add(newPost);
    }
}
