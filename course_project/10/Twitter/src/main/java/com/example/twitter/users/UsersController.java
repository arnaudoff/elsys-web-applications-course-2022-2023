package com.example.twitter.users;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {
    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public void registerUser(@RequestBody AddUserRequest addUserRequest){
        usersService.registerUser(addUserRequest);
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable int id){
        return usersService.getUser(id);
    }
}
