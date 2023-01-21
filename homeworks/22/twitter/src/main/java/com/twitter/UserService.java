package com.twitter;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Random random;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.random = new Random();
    }

    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return user.get();
    }
    public User register(User user) {
        if (userRepository.findByUsername(user.getUsername())!=null) {
            throw new IllegalArgumentException("User with that username already exists");
        }
        user.hashPassword();
        user.setFollowersCount(random.nextInt(10000000));
        user.setFollowingCount(random.nextInt(10000000));
        return userRepository.save(user);
    }
}