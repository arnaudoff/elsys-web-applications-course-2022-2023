package com.example.twitter;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TweetsService {
    public TweetsRepository tweetsRepository;

    public TweetsService(TweetsRepository tweetsRepository) {
        this.tweetsRepository = tweetsRepository;
    }

    public void addTweet(AddTweetRequest tweetRequest) {
        Tweet tweet = new Tweet();
        tweet.setId(1);
        tweet.setTweetDate(new Date());
        tweet.setTweetText(tweetRequest.getTweetText());
        tweet.setAuthor(tweetRequest.getAuthor());
        this.tweetsRepository.save(tweet);
    }

    public void removeTweet(long tweetId) {
        this.tweetsRepository.deleteById(tweetId);
    }

    public void updateTweet(long tweetId, UpdateTweetRequest tweetRequest) {
        Optional<Tweet> dbTweet = tweetsRepository.findById(tweetId);
        Tweet tweet = new Tweet();
        tweet.setId(tweetId);
        tweet.setTweetDate(dbTweet.get().getTweetDate());
        tweet.setTweetText(tweetRequest.getTweetText());
        tweet.setAuthor(dbTweet.get().getAuthor());
        this.tweetsRepository.save(tweet);
    }

    public Optional<Tweet> getTweet(long id) {
        return this.tweetsRepository.findById(id);
    }

    public List<Tweet> getAll() {
        return (List<Tweet>) this.tweetsRepository.findAll();
    }
}
