package com.project.twitter;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TweetController {

    private final List<Tweet> tweets;

    public TweetController(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    @PostMapping("/tweet")
    public Tweet createTweet(@RequestBody Tweet tweet) {
        tweet.setDate(LocalDateTime.now());
        tweets.add(tweet);
        return tweet;
    }

    @GetMapping("/tweet/{id}")
    public Tweet getTweet(@PathVariable Long id) {
        Optional<Tweet> tweet = tweets.stream().filter(t -> t.getId()==id).findFirst();
        return tweet.orElse(null);
    }

    @GetMapping("/tweet/user/{userId}")
    public List<Tweet> getUserTweets(@PathVariable Long userId) {
        return tweets.stream().filter(t -> t.getUserId()== userId).collect(Collectors.toList());
    }

    @DeleteMapping("/tweet/{id}")
    public void deleteTweet(@PathVariable Long id) {
        tweets.removeIf(t -> t.getId()==id);
    }

    @PutMapping("/tweet/{id}")
    public Tweet updateTweet(@PathVariable Long id, @RequestBody String text) {
        Optional<Tweet> tweet = tweets.stream().filter(t -> t.getId()==id).findFirst();
        if (tweet.isPresent()) {
            tweet.get().setText(text);
            return tweet.get();
        }
        return null;
    }
}
