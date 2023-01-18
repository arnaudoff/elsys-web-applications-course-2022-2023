package com.example.twitterapi;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController
{
	@PostMapping("/users")
	User newUser(@RequestBody String username)
	{
		return new User(username);
	}

	@GetMapping ("/users/{id}")
	User getUser(@PathVariable long id)
	{
		if (User.usersId.containsKey(id))
		{
			return User.usersId.get(id);
		}
		else
		{
			throw new UserNotFoundException(id);
		}
	}
}
