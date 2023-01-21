package com.example.elonmuskhw.services;

import com.example.elonmuskhw.DTOs.UserDTO;
import com.example.elonmuskhw.models.User;
import com.example.elonmuskhw.repositoryies.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void insertUser(String username) {
        User user = new User(username);
        userRepository.insertUser(user);
    }

    public UserDTO getUser(Integer id) {
        User user = userRepository.getUser(id);
        return new UserDTO(user.getUsername(), user.getRegistrationDate(), user.getFollowersCount(), user.getFollowingCount());
    }
}
