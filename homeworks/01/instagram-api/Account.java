package com.example.instagram_api;

import java.util.ArrayList;
import java.util.List;

public class Account {
    static int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    private List<Post> posts;

    public Account(String firstName, String lastName, String username, String password) {
        id ++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        posts = new ArrayList<>();
    }

    public static int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void addPost(Post post) {
        posts.add(post);
    }
    public List<Post> getPosts() {
        return posts;
    }

    @Override
    public String toString() {
        return "Account{" +
                "posts=" + posts +
                '}';
    }
}

