package org.elsys.courseproject;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TweetRepository {
    private List<Tweet> tweets;

    public TweetRepository() {
        tweets = new ArrayList<>();
    }

    public void insertTweet(Tweet tweet) {
        tweets.add(tweet);
    }

    public Tweet getTweet(String Id) {
        for (var tweet : tweets) {
            if(tweet.getId().equals(Id)) {
                return tweet;
            }
        }
        return null;
    }

    public List<Tweet> getAllTweets(){
        return tweets;
    }

    public List<Tweet> getTweetsByUserId(String id) {
        List<Tweet> tweetList = new ArrayList<>();
        for (var tweet : tweets) {
            if(tweet.getAuthorId().equals(id)) {
                tweetList.add(tweet);
            }
        }
        return tweetList;
    }

    public void deleteTweet(String id) {
        tweets.removeIf(tweet -> tweet.getId().equals(id));
    }

    public void updateTweet(String id, String text) {
        for (var tweet : tweets) {
            if (tweet.getId().equals(id)) {
                tweet.setText(text);
            }
        }
    }
}
