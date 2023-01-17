package com.example.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("/{id}")
    public Optional<Tweet> getTweet(@PathVariable Long id) {
        return tweetService.getTweet(id);
    }

    @PostMapping
    public ResponseEntity<Tweet> createTweet(@RequestBody Map<String,String> body) {
        Tweet createdTweet = tweetService.createTweet(body.get("text"),body.get("author"));
        return new ResponseEntity<>(createdTweet, HttpStatus.OK);
    }

    @GetMapping("/by-user/{id}")
    public List<Tweet> getTweetsByUser(@PathVariable Long id) {
        return tweetService.getTweetsByUser(id);
    }
    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable Long id) {
        tweetService.deleteTweet(id);
    }

    @PutMapping("/{id}")
    public void updateTweet(@PathVariable Long id, @RequestBody Tweet tweet) {
        tweetService.updateTweet(id, tweet);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}