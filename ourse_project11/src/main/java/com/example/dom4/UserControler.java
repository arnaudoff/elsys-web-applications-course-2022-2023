package com.example.dom4;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("User")
public class UserControler {
    private List<User> users = new ArrayList<>();

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody Map<String,String> userName) {
        User user =new User(userName.get("UserName"), users.size());
        user.setData(LocalDateTime.now());
        users.add(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
