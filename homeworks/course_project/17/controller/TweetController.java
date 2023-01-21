package com.firmata.elon.controller;

import com.firmata.elon.model.Tweet;
import com.firmata.elon.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TweetController {

    private final TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping("/tweets")
    public ResponseEntity<Tweet> create(@RequestBody Tweet tweet) {
        Tweet createdTweet = tweetService.createTweet(tweet);

        return new ResponseEntity<>(createdTweet, HttpStatus.CREATED);
    }

    @GetMapping("/tweets")
    public ResponseEntity<List<Tweet>> getAll() {
        return new ResponseEntity<>(tweetService.fetchAllTweet(), HttpStatus.OK);
    }

    @GetMapping("/tweets/{id}")
    public ResponseEntity<Optional<Tweet>> get(@PathVariable int id) {
        return new ResponseEntity<>(tweetService.fetchTweet(id), HttpStatus.OK);
    }

    @GetMapping("/tweets/by-user/{id}")
    public ResponseEntity<List<Tweet>> getUserTweets(@PathVariable int id) {
        return new ResponseEntity<>(tweetService.fetchAllByUser(id), HttpStatus.OK);
    }

    @PutMapping("/tweets/{id}")
    public ResponseEntity<Optional<Tweet>> update(@PathVariable int id, @RequestBody Tweet tweet) {
        return new ResponseEntity<>(tweetService.updateTweet(id, tweet), HttpStatus.CREATED);
    }

    @DeleteMapping("/tweets/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return new ResponseEntity<>(tweetService.deleteTweet(id), HttpStatus.ACCEPTED);
    }
}
