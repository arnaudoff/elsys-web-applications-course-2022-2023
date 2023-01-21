package com.example.homework4;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TweetsService {
    private TweetsRepository tweetsRepository;

    public TweetsService(TweetsRepository tweetsRepository) {
        this.tweetsRepository = tweetsRepository;
    }
    public void postTweet(TweetRequestProcessor tweetRequestProcessor){
        Tweet tweet = new Tweet();
        tweet.setText(tweetRequestProcessor.getText());
        tweet.setCreationDate(new Date());
        tweet.setUserId(tweetRequestProcessor.getAuthorId());
        tweet.assignId();
        tweetsRepository.createTweet(tweet);
    }

    public Optional<Tweet> getTweet(int id){
        return tweetsRepository.findById(id);
    }

    public List<Tweet> getTweetByAuthor(int user_id){
//        System.out.printf("user_id = %d", user_id);
        return tweetsRepository.findByAuthor(user_id);
    }

    public void deleteTweet(int id){
        tweetsRepository.deleteTweet(id);
    }

    public void updateTweet(int id, String text){
        tweetsRepository.changeTweet(id, text);
    }
}