package com.example.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TweetService {
    @Autowired
    private TweetRepository tweetRepository;

    public void createTweet(Tweet tweet) {
        tweet.setTweetDate(LocalDateTime.now());
        tweetRepository.save(tweet);
    }

    public Tweet getTweet(Long id) {
        return tweetRepository.findById(id)
                .orElseThrow(() -> new TweetNotFoundException(id));
    }

    public List<Tweet> getTweetsByUser(Long userId) {
        return tweetRepository.findByAuthorIdOrderByTweetDateDesc(userId);
    }

    public void deleteTweet(Long id) {
        tweetRepository.deleteById(id);
    }

    public void updateTweet(Long id, String tweetText) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new TweetNotFoundException(id));
        tweet.setTweetText(tweetText);
        tweetRepository.save(tweet);
    }
}
