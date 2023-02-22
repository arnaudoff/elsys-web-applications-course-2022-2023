package com.example.instagramhw.models;

import java.util.ArrayList;

public class User {
    private final int accountId;
    private static int count = 0;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    private static ArrayList<User> users = new ArrayList<>();

    private ArrayList<Post> posts = new ArrayList<>();

    public static ArrayList<User> getUsers() {
        return users;
    }

    public int getAccountId() {
        return accountId;
    }

    public void addUser(User user){
        users.add(user);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void makePost(Post post){
        posts.add(post);
    }

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        accountId = count;
        count++;
    }
}
