package com.example.proektinternet;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tweets")
public class TweetController {
    private List<Tweet> tweets = new ArrayList<>();
    private int tweetIdCounter = 1;

    @GetMapping("/{id}")
    public Tweet getTweet(@PathVariable int id) {
        for (Tweet tweet : tweets) {
            if (tweet.getId() == id) {
                return tweet;
            }
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<Void> postTweet(@RequestBody Map<String, String> tweetText)
    {
        User author = null;
        for (User user : UserController.users) {
            if (user.getId() == Integer.parseInt(tweetText.get("authorID"))) {
                author = user;
                break;

            }
        }
        if (author != null) {
            tweets.add(new Tweet(tweetIdCounter++, author, tweetText.get("text")));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/by-user/{id}")
    public List<Tweet> getUserTweets(@PathVariable int id) {
        List<Tweet> userTweets = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getAuthor().getId() == id) {
                userTweets.add(tweet);
            }
        }
        Collections.sort(userTweets, new Comparator<Tweet>() {
            @Override
            public int compare(Tweet t1, Tweet t2) {
                return t2.getTweetDate().compareTo(t1.getTweetDate());
            }
        });
        return userTweets;
    }

    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable int id) {
        Tweet tweetToDelete = null;
        for (Tweet tweet : tweets) {
            if (tweet.getId() == id) {
                tweetToDelete = tweet;
                break;
            }
        }
        if (tweetToDelete != null) {
            tweets.remove(tweetToDelete);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTweet(@PathVariable int id, @RequestBody Map<String, String> newTweetText) {
        for (Tweet tweet : tweets) {
            if (tweet.getId() == id) {
                tweet.setTweetText(newTweetText.get("newTweetText"));
                break;
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}