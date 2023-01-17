package com.example.homework4;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public class UsersRepository {
    private ArrayList<User> users = new ArrayList<>();

    public UsersRepository() {
    }

    public void register(User user){
        users.add(user);
    }
    public Optional<User> findById(int id){
        for (User user : users) {
            if (user.getId() == id) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
