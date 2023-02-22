package com.example.Twitter;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class UsersController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("users")
    public List<User> getAllUserNames(){
        return userRepository.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public void addUser(
            @RequestBody Map<String, String> newUser) throws SQLException {
        System.out.println(newUser.get("username"));
        userRepository.addUser(newUser.get("username"));
    }

    @RequestMapping("/users/{id}")
    public User getUser(@PathVariable int id){
        return userRepository.getUser(id);
    }
}
