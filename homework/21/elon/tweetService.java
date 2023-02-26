package com.example.elon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.elon.userService.getUserById;

@Service
public class TweetService {
    private List<Tweet> tweets = new ArrayList<>();
    private long tweetIdCounter = 0;

    public Tweet postTweet(String author, String text) {
        Tweet newTweet = new Tweet(++tweetIdCounter, author, text, new Date());
        tweets.add(newTweet);
        return newTweet;
    }

    public List<Tweet> getTweetsByUser(long id) {
        return tweets.stream()
                .filter(tweet -> tweet.getAuthor().equals(getUserById(id).getUsername()))
                .sorted(Comparator.comparing(Tweet::getDate).reversed())
                .collect(Collectors.toList());
    }

    public Tweet getTweetById(long id) {
        for (Tweet tweet : tweets) {
            if (tweet.getId() == id) {
                return tweet;
            }
        }
        return null;
    }

    public void deleteTweet(long id) {
        tweets.removeIf(tweet -> tweet.getId() == id);
    }

    public void updateTweet(long id, String text) {
        tweets.stream()
                .filter(tweet -> tweet.getId() == id)
                .findFirst()
                .ifPresent(tweet -> tweet.setText(text));
    }
}


