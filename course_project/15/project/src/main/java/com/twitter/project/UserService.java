package com.twitter.project;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository usersRepository) {
        this.userRepository = usersRepository;
    }

    public void addUser(AddUserRequest addUserRequest){
        User user = new User();
        user.setUsername(addUserRequest.getUsername());
        user.setRegistrationDate(LocalDateTime.now());
        user.setFollowersCount(new Random().nextInt());
        user.setFollowingCount(new Random().nextInt());
    }

    public Optional<User> getUser(int id){
        return userRepository.findById(id);
    }
}
