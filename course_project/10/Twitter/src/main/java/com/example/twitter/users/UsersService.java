package com.example.twitter.users;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    private UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    public void registerUser(AddUserRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setFollowing_count(100);
        user.setFollowers_count(100);
        user.setDate_registered(userRequest.getDate_registered());
        usersRepository.register(user);
    }
    public Optional<User> getUser(int id){
        return usersRepository.findById(id);
    }
}
