package org.elsys.courseproject;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> users;

    public UserRepository() {
        users = new ArrayList<>();
    }

    public void insertUser(User user) {
        users.add(user);
    }

    public User getUser(String Id) {
        for (var user : users) {
            if (user.getId().equals(Id)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return users;
    }
}
