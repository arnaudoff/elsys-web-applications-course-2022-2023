package com.example.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tweets")
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @PostMapping
    public ResponseEntity<Void> createTweet(@RequestBody Tweet tweet) {
        tweetService.createTweet(tweet);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tweet> getTweet(@PathVariable Long id) {
        Tweet tweet = tweetService.getTweet(id);
        return new ResponseEntity<>(tweet, HttpStatus.OK);
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<Tweet>> getTweetsByUser(@PathVariable Long id) {
        List<Tweet> tweets = tweetService.getTweetsByUser(id);
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable Long id) {
        tweetService.deleteTweet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTweet(@PathVariable Long id, @RequestBody Map<String, String> tweet) {
        tweetService.updateTweet(id, tweet.get("tweetText"));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(TweetNotFoundException.class)
    public ResponseEntity<String> handleTweetNotFound(TweetNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}

