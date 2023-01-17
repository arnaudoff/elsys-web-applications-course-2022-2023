package com.example.proektinternet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private User main = new User();
    private int userIdCounter = 1;
    public static List<User> users = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Void> registerUser(@RequestBody Map<String, String> username) {
        users.add(new User(userIdCounter++, username.get("username")));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public List<User> getUsers() {
        return users;
    }
}