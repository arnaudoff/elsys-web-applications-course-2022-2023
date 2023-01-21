package org.elsysbg.twitterapi.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService; 
    
    @PostMapping
    public void insertUser(@RequestBody UserRequest userRequest) {
       userService.insertUser(userRequest);
    }
    
    @GetMapping("/{userId}")
    public Optional<User> fetchUser(@PathVariable long userId) {
        return userService.fetchFromId(userId);
    }
}
