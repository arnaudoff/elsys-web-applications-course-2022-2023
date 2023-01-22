package com.example.twitter;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("tweets")
public class TweetsController {

    private TweetsService tweetsService;

    public TweetsController(TweetsService tweetsService) {
        this.tweetsService = tweetsService;
    }

    @PostMapping
    public void addTweet(@RequestBody AddTweetRequest addTweetRequest) {
        this.tweetsService.addTweet(addTweetRequest);
    }

    @GetMapping("/{tweetId}")
    public Optional<Tweet> getTweet(@PathVariable long tweetId) {
        return this.tweetsService.getTweet(tweetId);
    }

    @GetMapping("/by-user/{userId}")
    public List<Tweet> getTweets(@PathVariable long userId) {
        return this.tweetsService.getAll().stream()
                .filter(tweet -> tweet.getAuthor().getId() == userId)
                .sorted((tweet, otherTweet) -> otherTweet.getTweetDate().compareTo(tweet.getTweetDate()))
                .toList();
    }

    @DeleteMapping("/{tweetId}")
    public void deleteTweet(@PathVariable long tweetId) {
        this.tweetsService.removeTweet(tweetId);
    }

    @PutMapping("/{tweetId}")
    public void updateTweet(@PathVariable long tweetId, @RequestBody UpdateTweetRequest updateTweetRequest) {
        this.tweetsService.updateTweet(tweetId, updateTweetRequest);
    }
}
