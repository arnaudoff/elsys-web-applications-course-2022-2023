package com.project.twitter;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    // A list to hold all the users
    private List<User> users = new ArrayList<>();

    public User createUser(User user) {
        // set the user's ID
        user.setId((long) users.size());
        // add the user to the list of users
        users.add(user);
        return user;
    }

    public User getUser(Long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void deleteUser(Long id) {
        users.removeIf(user -> user.getId() == id);
    }
}
