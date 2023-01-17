package org.elsys.courseproject;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(String username) {
        User user = new User(username);
        userRepository.insertUser(user);
    }

    public UserResponse getUser(String Id) {
        User user =  userRepository.getUser(Id);
        return new UserResponse(user.getUsername(), user.getRegistrationDate(), user.getFollowersCount(), user.getFollowingCount());
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
