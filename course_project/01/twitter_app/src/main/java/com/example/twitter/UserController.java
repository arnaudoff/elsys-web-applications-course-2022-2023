package com.example.twitter;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public void addUser(@RequestBody UserRequest userRequest) {
        userRepository.addUser(userRequest.getUsername());
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable int userId) {
        return userRepository.findById(userId);
    }
}

