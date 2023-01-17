package com.example.elonmuskhw.controllers;

import com.example.elonmuskhw.DTOs.TweetDTO;
import com.example.elonmuskhw.DTOs.TweetResponse;
import com.example.elonmuskhw.DTOs.TweetUpdateRequest;
import com.example.elonmuskhw.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tweets")
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @GetMapping("/{id}")
    public TweetResponse GetTweet(@PathVariable Integer id) {
        return tweetService.getTweet(id);
    }

    @PostMapping
    public void PostTweet(@RequestBody TweetDTO tweetDTO) {
        tweetService.insertTweet(tweetDTO.getUserId(), tweetDTO.getText());
    }

    @GetMapping("/by-user/{id}")
    public List<TweetResponse> GetTweetByUser(@PathVariable Integer id) {
        return tweetService.getTweetsDescending(id);
    }

    @DeleteMapping("/{id}")
    public void DeleteTweet(@PathVariable Integer id) {
        tweetService.deleteTweet(id);
    }

    @PutMapping("/{id}")
    public void UpdateTweet(@PathVariable Integer id, @RequestBody TweetUpdateRequest tweetUpdateRequest) {
        tweetService.updateTweet(id, tweetUpdateRequest);
    }

}
