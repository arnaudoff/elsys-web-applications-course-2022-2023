package com.example.course_project;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository
public class UsersRepository {
    private HashMap<Long, User> users;

    public UsersRepository() {
        users = new HashMap<>();
    }

    public void addUser(User newUser) {
        users.put(newUser.getId(), newUser);
    }

    public Optional<User> getUser(long id) {
        return Optional.ofNullable(users.get(id));
    }
}
