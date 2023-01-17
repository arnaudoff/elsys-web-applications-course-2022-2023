package com.example.twitter;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("users")
public class UsersController {
    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public void addUser(@RequestBody AddUserRequest addUserRequest) {
        this.usersService.addUser(addUserRequest);
    }

    @GetMapping("/{userId}")
    public Optional<User> getUser(@PathVariable long userId) {
        return this.usersService.getUser(userId);
    }
}
