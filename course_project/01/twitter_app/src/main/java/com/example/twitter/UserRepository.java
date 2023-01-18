package com.example.twitter;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    public void addUser(String username) {
        this.users.add(new User(username));
    }

    public User findById(int id) {
        return this.users.stream()
                .filter(user -> user.getId() == id)
                .findFirst().get();
    }
}
