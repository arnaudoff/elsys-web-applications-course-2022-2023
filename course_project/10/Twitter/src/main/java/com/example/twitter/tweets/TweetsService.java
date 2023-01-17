package com.example.twitter.tweets;

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
    public void postTweet(AddTweetRequest addTweetRequest){
        Tweet tweet = new Tweet();
        tweet.setText(addTweetRequest.getText());
        tweet.setDate_created(new Date());
        tweet.setUser_id(addTweetRequest.getUser_id());
        tweetsRepository.createTweet(tweet);
    }

    public Optional<Tweet> getTweet(int id){
        return tweetsRepository.findById(id);
    }

    public List<Tweet> getTweetByAuthor(int user_id){
        return tweetsRepository.findByAuthor(user_id);
    }

    public void deleteTweet(int id){
        tweetsRepository.deleteTweet(id);
    }

    public void updateTweet(int id, String text){
        tweetsRepository.changeTweet(id, text);
    }
}
