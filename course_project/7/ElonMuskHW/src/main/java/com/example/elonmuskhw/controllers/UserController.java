package com.example.elonmuskhw.controllers;

import com.example.elonmuskhw.DTOs.RegisterDTO;
import com.example.elonmuskhw.DTOs.UserDTO;
import com.example.elonmuskhw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public void Register(@RequestBody RegisterDTO registerDTO) {
        userService.insertUser(registerDTO.getUsername());
    }

    @GetMapping("/{id}")
    public UserDTO GetUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }
}
