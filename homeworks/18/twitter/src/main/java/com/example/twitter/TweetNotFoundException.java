package com.example.twitter;

public class TweetNotFoundException extends RuntimeException {

    public TweetNotFoundException(Long id) {
        super("Could not find tweet " + id);
    }
}

