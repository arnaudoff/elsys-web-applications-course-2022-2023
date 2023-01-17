package com.twitter;

public class TweetNotFoundException extends RuntimeException {
    public TweetNotFoundException(Long id) {
        super("Tweet with id: " + id + " not found");
    }
}