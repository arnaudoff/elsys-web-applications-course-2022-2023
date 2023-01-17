package com.example.tweet;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private static List<User> users = new ArrayList<>();
    private long userIdCounter = 0;

    public User registerUser(String username) {
        var newUser = new User(++userIdCounter,username,new Date());
        users.add(newUser);
        return newUser;
    }

    public static User getUserById(long id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}



