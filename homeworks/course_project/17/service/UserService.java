package com.firmata.elon.service;

import com.firmata.elon.model.User;
import com.firmata.elon.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> updateUser(@NonNull User newUser) {
        return userRepository.findById(newUser.getId())
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    return userRepository.save(user);
                });
    }


}
