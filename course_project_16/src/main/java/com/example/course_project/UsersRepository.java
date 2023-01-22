package com.example.course_project;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class UsersRepository {
    private HashMap<Long, User> users = new HashMap<>();

    public void addUser(String username) {
        var user = new User(username);
        users.put(user.getId(), user);
    }

    public User getUser(long id) {
        return users.get(id);
    }
}
