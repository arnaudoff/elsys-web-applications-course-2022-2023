package com.example.course_project;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tweets")
public class TweetsController {
    private TweetsService tweetsService;

    public TweetsController(TweetsService tweetsService) {
        this.tweetsService = tweetsService;
    }

    @GetMapping
    public List<Tweet> getTweets() {
        return tweetsService.getTweets();
    }

    @GetMapping("/{id}")
    public Optional<Tweet> getTweetById(@PathVariable long id) {
        return tweetsService.getTweetById(id);
    }

    @PostMapping
    public void addTweet(@RequestBody TweetRequest newTweet) {
        tweetsService.addTweet(new Tweet(newTweet.getAuthorId(), newTweet.getText()));
    }

    @DeleteMapping("/{id}")
    public void removeTweet(@PathVariable long id) {
        tweetsService.removeTweet(id);
    }

    @PutMapping("/{id}")
    public void updateTweetText(@PathVariable long id, @RequestBody TweetRequest newTweet) {
        tweetsService.updateTweetText(id, newTweet.getText());
    }
}
