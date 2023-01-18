package com.project.twitter;

import java.util.List;
import java.util.ArrayList;

public class TweetService {

    private List<Tweet> tweets = new ArrayList<>();

    public Tweet createTweet(Tweet tweet) {
        // set the tweet's ID
        tweet.setId((long) tweets.size());
        // add the tweet to the list of tweets
        tweets.add(tweet);
        return tweet;
    }

    public Tweet getTweet(Long id) {
        for (Tweet tweet : tweets) {
            if (tweet.getId()==id) {
                return tweet;
            }
        }
        return null;
    }

    public List<Tweet> getUserTweets(Long userId) {
        List<Tweet> userTweets = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getUserId()==userId) {
                userTweets.add(tweet);
            }
        }
        return userTweets;
    }

    public void deleteTweet(Long id) {
        tweets.removeIf(tweet -> tweet.getId() == id);
    }

    public Tweet updateTweet(Long id, String text) {
        for (Tweet tweet : tweets) {
            if (tweet.getId()==id) {
                tweet.setText(text);
                return tweet;
            }
        }
        return null;
    }
}
