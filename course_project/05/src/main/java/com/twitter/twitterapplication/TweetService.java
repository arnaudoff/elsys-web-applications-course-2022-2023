package com.twitter.twitterapplication;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class TweetService {
    private TweetRepository tweetRepository;

    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }
    public void postTweet(AddTweetRequest addTweetRequest){
        Tweet tweet = new Tweet();
        tweet.setText(addTweetRequest.getText());
        tweet.setDateCreated(new Date());
        tweet.setAuthorId(addTweetRequest.getAuthorId());
        tweetRepository.createTweet(tweet);
    }

    public Tweet getTweet(int id){
        return tweetRepository.findById(id);
    }

    public ArrayList<Tweet> getTweetByAuthor(int user_id){
        return tweetRepository.findByAuthor(user_id);
    }

    public void deleteTweet(int id){
        tweetRepository.deleteTweet(id);
    }

    public void updateTweet(int id, String text){
        tweetRepository.changeTweet(id, text);
    }
}
