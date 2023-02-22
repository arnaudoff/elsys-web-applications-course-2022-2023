package com.homework.twitter;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/twitter")
public class TwitterController {
    private UserService userService;
    private TweetService tweetService;

    public TwitterController(UserService userService, TweetService tweetService) {
        this.userService = userService;
        this.tweetService = tweetService;
    }

    @PostMapping("/users")
    public void registerUser(@RequestBody AddUserRequest request) {
        userService.addUser(request);
    }

    @GetMapping("/users/{id}")
    public GetUserResponse getUserById(@PathVariable long id) {
        return userService.getUser(id);
    }

    @GetMapping("/tweets/{id}")
    public GetTweetResponse getTweetById(@PathVariable long id) {
        return tweetService.getTweet(id);
    }

    @PostMapping("/tweets")
    public Tweet addTweet(@RequestBody AddTweetRequest request) {
        return tweetService.addTweet(request);
    }

    @GetMapping("/tweets/by-user/{id}")
    public List<GetTweetResponse> getTweetByUserAndId(@PathVariable long id) {
        return tweetService.getTweetsByUserId(id);
    }

    @PutMapping("/tweets/{id}")
    public void updateTweetById(@PathVariable long id, @RequestBody UpdateTweetRequest request) {
        this.tweetService.updateTweet(id, request);
    }

    @DeleteMapping("/tweets/{id}")
    public void deleteTweetById(@PathVariable long id) {
        this.tweetService.deleteTweet(id);
    }
}
