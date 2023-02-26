package com.example.tweet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @PostMapping
    public ResponseEntity<Tweet> postTweet(@RequestBody TweetRequest tweetRequest) {
        var newTweet = tweetService.postTweet(tweetRequest.getAuthor(), tweetRequest.getText());
        return new ResponseEntity<>(newTweet, HttpStatus.CREATED);
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<Tweet>> getTweetsByUser(@PathVariable long id) {
        var tweets = tweetService.getTweetsByUser(id);
        return tweets.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(tweets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tweet> getTweetDetails(@PathVariable long id) {
        var tweet = tweetService.getTweetById(id);
        return tweet != null ? new ResponseEntity<>(tweet, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable long id) {
        tweetService.deleteTweet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tweet> updateTweet(@PathVariable long id, @RequestBody String text) {
        var tweet = tweetService.getTweetById(id);
        if (tweet != null) {
            tweetService.updateTweet(id, text);
            return new ResponseEntity<>(tweet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}






