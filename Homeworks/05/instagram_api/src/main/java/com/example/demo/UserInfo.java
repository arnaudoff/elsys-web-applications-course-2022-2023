package com.example.demo;

public class UserInfo {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private int password;

    public UserInfo(int id, String firstName, String lastName, String username, int password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public int getPassword() {
        return password;
    }
}
