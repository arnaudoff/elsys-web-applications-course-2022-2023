package com.example.twitter;

import java.util.*;

public class TweetRepository {
    private Map<Integer, Tweet> tweets;

    public TweetRepository() {
        this.tweets = new HashMap<>();
    }

    public void postTweet(Tweet tweet) {
        this.tweets.put(tweet.getId(), tweet);
    }

    public void deleteTweet(int id) {
        this.tweets.remove(id);
    }

    public Tweet getTweetById(int id) {
        return this.tweets.get(id);
    }

    public List<Tweet> getTweetsByUserId(int authorId) {
        return this.tweets.values().stream()
                .filter(tweet -> tweet.getAuthor().getId() == authorId)
                .sorted((tweet, otherTweet) -> (int) (otherTweet.getDate().compareTo(tweet.getDate())))
                .toList();
    }

    public void putTweetText(int id, String text) {
        this.tweets.get(id).setText(text);
    }
}
