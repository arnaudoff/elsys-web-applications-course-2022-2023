package com.example.course_project;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("users")
public class UsersController {
    private UsersService userService;

    public UsersController(UsersService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void addUser(@RequestBody UserRequest newUser) {
        userService.addUser(new User(newUser.getUsername()));
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable long id) {
        return userService.getUser(id);
    }
}
