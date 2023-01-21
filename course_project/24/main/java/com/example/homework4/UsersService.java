package com.example.homework4;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    private UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    public void registerUser(UserRequestProcessor userRequest){
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setFollowingCount(77);
        user.setFollowersCount(55);
        user.assignId();
        user.setDateRegistered(userRequest.getDateRegistered());
        usersRepository.register(user);
    }
    public Optional<User> getUser(int id){
        return usersRepository.findById(id);
    }
}