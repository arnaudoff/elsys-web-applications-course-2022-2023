package com.twitter.twitterapplication;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class Controller {
    private TweetService tweetService;
    private UserService userService;

    public Controller(TweetService tweetService, UserService userService) {
        this.tweetService = tweetService;
        this.userService = userService;
    }

    @PostMapping("tweets")
    public void postTweet(@RequestBody AddTweetRequest addTweetRequest){
        tweetService.postTweet(addTweetRequest);
    }

    @GetMapping("tweets/{id}")
    public Tweet getTweet(@PathVariable int id){
        return tweetService.getTweet(id);
    }

    @GetMapping("tweets/by-user/{id}")
    public ArrayList<Tweet> getTweetsByAuthor(@PathVariable int id){
        return tweetService.getTweetByAuthor(id);
    }

    @DeleteMapping("tweets/{id}")
    public void deleteTweet(@PathVariable int id){
        tweetService.deleteTweet(id);
    }

    @PutMapping("tweets/{id}")
    public void updateTweet(@PathVariable int id, @RequestBody String text){
        tweetService.updateTweet(id, text);
    }

    @PostMapping("users")
    public void registerUser(@RequestBody RegisterUserRequest registerUserRequest){
        userService.registerUser(registerUserRequest);
    }

    @GetMapping("users/{id}")
    public User getUserById(@PathVariable int id){
        return userService.getUserById(id);
    }
}
