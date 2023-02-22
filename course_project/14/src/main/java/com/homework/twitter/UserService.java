package com.homework.twitter;


import java.util.Optional;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;


@Service
public class UserService {
    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User addUser(AddUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setFollowersCount(new Random().nextLong() & 0xffffffffL);
        user.setFollowingCount(new Random().nextLong() & 0xffffffffL);
        user.setRegistrationDate(LocalDateTime.now());
        return usersRepository.save(user);
    }

    public GetUserResponse getUser(long id) {
        Optional<User> optionalUser = this.usersRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            GetUserResponse response = new GetUserResponse();
            response.setId(user.getId());
            response.setUsername(user.getUsername());
            response.setRegistrationDate(user.getRegistrationDate());
            response.setFollowersCount(user.getFollowersCount());
            response.setFollowingCount(user.getFollowingCount());
            return response;
        } else {
            try {
                throw new Exception("User with id " + id + " not found");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
