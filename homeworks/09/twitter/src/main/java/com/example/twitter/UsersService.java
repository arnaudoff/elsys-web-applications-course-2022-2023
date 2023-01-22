package com.example.twitter;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    private UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void addUser(AddUserRequest userRequest) {
        User user = new User();
        user.setId(1);
        user.setUsername(userRequest.getUsername());
        user.setFollowers(100);
        user.setFollowing(200);
        user.setRegistrationDate(new Date());

        this.usersRepository.save(user);
    }

    public Optional<User> getUser(long id) {
        return this.usersRepository.findById(id);
    }
}
