package com.twitter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping
    public ResponseEntity<Tweet> createTweet(@RequestBody Map<String, String> request) {
        String authorUsername = request.get("author");
        String text = request.get("text");
        Tweet tweet = tweetService.createTweet(authorUsername, text);
        return new ResponseEntity<>(tweet, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tweet> getTweet(@PathVariable Long id) {
        Tweet tweet = tweetService.getTweet(id);
        return new ResponseEntity<>(tweet, HttpStatus.OK);
    }
    @ExceptionHandler(TweetNotFoundException.class)
    public ResponseEntity<String> handleTweetNotFound(TweetNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<Tweet>> getTweetsByUser(@PathVariable Long id) {
        List<Tweet> tweets = tweetService.getTweetsByUser(id);
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleTweetNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable Long id) {
        tweetService.deleteTweet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tweet> updateTweet(@PathVariable Long id, @RequestBody Tweet tweet) {
        Tweet updatedTweet = tweetService.updateTweet(id, tweet);
        return new ResponseEntity<>(updatedTweet, HttpStatus.OK);
    }
}