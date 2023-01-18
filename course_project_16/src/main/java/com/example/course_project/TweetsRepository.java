package com.example.course_project;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class TweetsRepository {
    private HashMap<Long, Tweet> tweets = new HashMap<>();

    public List<Tweet> getTweets() {
        return (List<Tweet>) tweets.values();
    }

    public Tweet getTweet(long id) {
        return tweets.get(id);
    }

    public void addTweet(Tweet tweet) {
        tweets.put(tweet.getId(), tweet);
    }

    public void removeTweet(long id) {
        tweets.remove(id);
    }

    public void updateTweet(long id, String text) {
        tweets.get(id).setText(text);
    }
}
