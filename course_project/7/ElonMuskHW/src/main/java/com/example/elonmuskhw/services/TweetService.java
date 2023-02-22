package com.example.elonmuskhw.services;

import com.example.elonmuskhw.DTOs.TweetResponse;
import com.example.elonmuskhw.DTOs.TweetUpdateRequest;
import com.example.elonmuskhw.models.Tweet;
import com.example.elonmuskhw.models.User;
import com.example.elonmuskhw.repositoryies.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TweetService {
    @Autowired
    private TweetRepository tweetRepository;

    public void insertTweet(Integer userId, String text) {
        User user = tweetRepository.getUserById(userId);
        Tweet tweet = new Tweet(user, text);
        tweetRepository.insertTweet(tweet);
    }

    public TweetResponse getTweet(Integer id) {
        Tweet tweet = tweetRepository.getTweet(id);
        return new TweetResponse(tweet.getText(), tweet.getTweetDate());
    }

    public List<TweetResponse> getTweetsDescending(Integer userId) {
        List<Tweet> tweets = tweetRepository.getTweets(userId);
        List<TweetResponse> tweetResponses = new ArrayList<>();
        tweets.forEach(tweet -> {
            tweetResponses.add(new TweetResponse(tweet.getText(), tweet.getTweetDate()));
        });
        tweetResponses.sort(null);
        return tweetResponses;
    }

    public void deleteTweet(Integer tweetId) {
        tweetRepository.deleteTweet(tweetId);
    }

    public void updateTweet(Integer tweetId, TweetUpdateRequest tweetUpdateRequest) {
        tweetRepository.updateTweet(tweetId, tweetUpdateRequest);
    }
}
