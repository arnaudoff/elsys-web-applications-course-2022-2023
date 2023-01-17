package com.example.course_project;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("users")
public class UsersController {
    private UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @PostMapping
    public void addUser(@RequestBody UserRequest userRequest) {
        usersRepository.addUser(userRequest.getUsername());
    }

    @GetMapping("/{userId}")
    public Optional<User> getUser(@PathVariable long userId) {
        return usersRepository.getUser(userId);
    }
}
