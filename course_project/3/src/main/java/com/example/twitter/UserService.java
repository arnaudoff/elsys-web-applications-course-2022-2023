package com.example.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if(userRepository.findByUsername(user.getUsername()) != null){
            throw new IllegalArgumentException("Username already exists");
        }
        user.setRegistrationDate(new Date());
        user.setFollowersCount(0);
        user.setFollowingCount(0);
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}