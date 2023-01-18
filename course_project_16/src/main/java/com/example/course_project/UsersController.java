package com.example.course_project;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UsersController {
    private UsersRepository usersRepository;

    @PostMapping
    public void addUser(@RequestBody UserRequest userRequest) {
        usersRepository.addUser(userRequest.getUsername());
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable long userId) {
        return usersRepository.getUser(userId);
    }
}
