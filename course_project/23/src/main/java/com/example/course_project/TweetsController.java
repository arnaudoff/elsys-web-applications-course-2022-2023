package com.example.course_project;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tweets")
public class TweetsController {
    private TweetsRepository tweetsRepository;

    public TweetsController(TweetsRepository tweetsRepository) {
        this.tweetsRepository = tweetsRepository;
    }
    @GetMapping("/{tweetId}")
    public Optional<Tweet> getTweet(@PathVariable long tweetId) {
        return tweetsRepository.getTweet(tweetId);
    }

    @PostMapping
    public void addTweet(@RequestBody TweetRequest tweetRequest) {
        tweetsRepository.addTweet(tweetRequest.getAuthor(), tweetRequest.getText());
    }

    @GetMapping("/by-user/{userId}")
    public List<Tweet> getTweetsByUser(@PathVariable long userId) {
        return tweetsRepository.getTweetsByUser(userId);
    }

    @DeleteMapping("/{tweetId}")
    public void removeTweet(@PathVariable long tweetId) {
        tweetsRepository.removeTweet(tweetId);
    }

    @PutMapping("/{tweetId}")
    public void updateTweet(@PathVariable long tweetId, @RequestBody TweetRequest tweetRequest) {
        tweetsRepository.updateTweet(tweetId, tweetRequest.getText());
    }
}
