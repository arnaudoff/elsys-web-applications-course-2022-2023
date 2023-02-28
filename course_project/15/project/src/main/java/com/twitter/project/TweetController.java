package com.twitter.project;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service

public class TweetController {

    private TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping("/tweets")
    public void addTweet(@RequestBody AddTweetRequest request) {
        tweetService.addTweet(request);
    }

    @GetMapping("/tweets/{id}")
    public Optional<Tweet> getTweetById(@PathVariable int id) {
        return tweetService.getTweet(id);
    }

    @GetMapping("/tweets/by-user/{id}")
    public List<Tweet> getTweetByAuthorId(@PathVariable int id) {
        return tweetService.getTweetByAuthor(id);
    }

    @DeleteMapping("/tweets/{id}")
    public void deleteTweetById(@PathVariable int id) {
        this.tweetService.deleteTweet(id);
    }

    @PutMapping("/tweets/{id}")
    public void updateTweetById(@PathVariable int id, @RequestBody UpdateTweetRequest request) {
        this.tweetService.updateTweet(id, request.getTweetText());
    }
}
