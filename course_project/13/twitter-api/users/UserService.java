package org.elsysbg.twitterapi.users;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public void insertUser(UserRequest userRequest) {
        userRepository.insert(new User(
            0,  // Doesn't matter since id is written by the DB  
            userRequest.getUsername(),
            LocalDateTime.now(),
            0,
            0
        ));
    }
    
    public Optional<User> fetchFromId(long id) {
        return userRepository.fetch(id);
    }
}
