package com.example.twitter.tweets;

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

    @PostMapping
    public void postTweet(@RequestBody AddTweetRequest addTweetRequest){
        tweetsService.postTweet(addTweetRequest);
    }

    @GetMapping("/{id}")
    public Optional<Tweet> getTweet(@PathVariable int id){
        return tweetsService.getTweet(id);
    }

    @GetMapping("/by-user/{id}")
    public List<Tweet> getTweetsByAuthor(@PathVariable int id){
        return tweetsService.getTweetByAuthor(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable int id){
        tweetsService.deleteTweet(id);
    }

    @PutMapping("/{id}")
    public void updateTweet(@PathVariable int id, @RequestBody UpdateTweetRequest updateTweetRequest){
        tweetsService.updateTweet(id, updateTweetRequest.getText());
    }
}
