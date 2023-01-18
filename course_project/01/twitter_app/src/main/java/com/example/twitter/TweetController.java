package com.example.twitter;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tweets")
public class TweetController {
    private TweetRepository tweetRepository;

    public TweetController(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @GetMapping("/{tweetId}")
    public Tweet getTweet(@PathVariable int tweetId) {
        return tweetRepository.getTweetById(tweetId);
    }

    @PostMapping
    public void addTweet(@RequestBody TweetRequest tweetRequest) {
        tweetRepository.postTweet(new Tweet(tweetRequest.getAuthor(), tweetRequest.getText()));
    }

    @GetMapping("/by-user/{userId}")
    public List<Tweet> getTweetsByUser(@PathVariable int userId) {
        return tweetRepository.getTweetsByUserId(userId);
    }

    @DeleteMapping("/{tweetId}")
    public void removeTweet(@PathVariable int tweetId) {
        tweetRepository.deleteTweet(tweetId);
    }

    @PutMapping("/{tweetId}")
    public void updateTweet(@PathVariable int tweetId, @RequestBody TweetRequest tweetRequest) {
        tweetRepository.putTweetText(tweetId, tweetRequest.getText());
    }
}

