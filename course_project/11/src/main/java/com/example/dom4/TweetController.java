package com.example.dom4;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tweets")
public class TweetController {
    private List<Tweets> tweets = new ArrayList<>();

    @GetMapping("/{id}")
    public ResponseEntity<Tweets> getTweetById(@PathVariable int id) {
        Tweets tweet = tweets.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);

        if (tweet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(tweet, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<String> createTweet(@RequestBody TweetDTO tweet) {
        Tweets tweets1 = new Tweets(tweets.size(),tweet.author,tweet.Description);
        tweets.add(tweets1);
        return new ResponseEntity<>("Tweet created successfully", HttpStatus.CREATED);
    }
    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<Tweets>> getTweetsByUser(@PathVariable int id) {
        List<Tweets> userTweets = tweets.stream()
                .filter(t -> t.getId() == id)
                .sorted(Comparator.comparing(Tweets::getData).reversed())
                .collect(Collectors.toList());

        if (userTweets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userTweets, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTweet(@PathVariable int id) {
        boolean deleted = tweets.removeIf(t -> t.getId() == id);

        if (!deleted) {
            return new ResponseEntity<>("Tweet not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Tweet deleted successfully", HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTweet(@PathVariable int id, @RequestBody String newMessage) {
        Tweets tweet = tweets.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);

        if (tweet == null) {
            return new ResponseEntity<>("Tweet not found", HttpStatus.NOT_FOUND);
        }

        tweet.setDescription(newMessage);
        return new ResponseEntity<>("Tweet updated successfully", HttpStatus.OK);
    }
}

