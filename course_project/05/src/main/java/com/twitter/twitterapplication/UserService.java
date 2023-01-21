package com.twitter.twitterapplication;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id){
        return userRepository.getUserById(id);
    }

    public void registerUser(RegisterUserRequest registerUserRequest){
        User user = new User();
        user.setDateRegistered(new Date());
        user.setUsername(registerUserRequest.getUsername());
        user.setFollowersCount(7);
        user.setFollowingCount(17);
        userRepository.registerUser(user);

    }
}
