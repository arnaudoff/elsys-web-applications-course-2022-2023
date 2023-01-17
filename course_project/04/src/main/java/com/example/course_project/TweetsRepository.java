package com.example.course_project;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class TweetsRepository {
    private HashMap<Long, Tweet> tweets;

    public TweetsRepository() {
        tweets = new HashMap<>();
    }

    public List<Tweet> getTweets() {
        return tweets.values().stream().toList();
    }

    public Optional<Tweet> getTweetById(long id) {
        return Optional.ofNullable(tweets.get(id));
    }

    public void addTweet(Tweet newTweet) {
        tweets.put(newTweet.getId(), newTweet);
    }

    public void removeTweet(long id) {
        tweets.remove(id);
    }

    public void updateTweetText(long id, String newText) {
        getTweetById(id).ifPresent(value -> value.setText(newText));
    }
}
