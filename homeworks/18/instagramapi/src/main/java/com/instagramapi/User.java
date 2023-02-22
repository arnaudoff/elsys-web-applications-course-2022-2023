package com.instagramapi;

import java.util.HashMap;

public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    private static final HashMap<Long, User> users = new HashMap<>();

    public static void addUser(User user) {
        users.put(user.getId(), user);
    }

    public static User getUser(long id) {
        return users.get(id);
    }

    public long getId() {
        return id;
    }
    private static long idCounter = 0;

    public void generateId() {
        this.id = idCounter++;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
