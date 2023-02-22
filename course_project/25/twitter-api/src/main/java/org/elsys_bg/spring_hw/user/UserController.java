package org.elsys_bg.spring_hw.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/{id}")
	public Optional<User> getUser(@PathVariable int id) {
		return userRepository.findById(id);
	}

	@PostMapping
	public void registerUser(@RequestBody RegisterUserRequest request) {
		userRepository.save(new User(request.getName()));
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}
}
