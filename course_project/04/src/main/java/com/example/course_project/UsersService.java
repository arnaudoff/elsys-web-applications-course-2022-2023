package com.example.course_project;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    private UsersRepository usersRepository;

    public UsersService(UsersRepository newUsersRepository) {
        usersRepository = newUsersRepository;
    }

    public void addUser(User newUser) {
        usersRepository.addUser(newUser);
    }

    public Optional<User> getUser(long id) {
        return usersRepository.getUser(id);
    }
}
