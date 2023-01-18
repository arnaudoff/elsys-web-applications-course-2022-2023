package com.example.elon;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static List<User> users = new ArrayList<>();
    private long userIdCounter = 0;

    public User getUserById(long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User registerUser(String username) {
        var newUser = new User(++userIdCounter,username,new Date());
        users.add(newUser);
        return newUser;
    }
}


