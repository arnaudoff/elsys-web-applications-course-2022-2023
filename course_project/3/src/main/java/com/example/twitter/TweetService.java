package com.example.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    @Autowired
    public TweetService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    public Optional<Tweet> getTweet(Long id) {
        return tweetRepository.findById(id);
    }

    public Tweet createTweet(String text , String username) {
        Tweet tweet = new Tweet();
        tweet.setText(text);
        User author = userRepository.findByUsername(username);
        if(author == null){
            throw new IllegalArgumentException("NO such user");
        }
        tweet.setAuthor(author);
        tweet.setDate(new Date());
        return tweetRepository.save(tweet);
    }

    public List<Tweet> getTweetsByUser(Long id) {
        return tweetRepository.findByAuthorIdOrderByTweetDateDesc(id);
    }

    public void deleteTweet(Long id) {
        if(tweetRepository.findById(id).isEmpty()){
            throw new IllegalArgumentException("No such tweet");
        }
        tweetRepository.deleteById(id);
    }

    public void updateTweet(Long id, Tweet tweetr) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if(tweet.isEmpty()){
            throw new IllegalArgumentException("No such tweet");
        }
        Tweet tweet1 = tweet.get();
        tweet1.setText(tweetr.getText());
        tweetRepository.save(tweet1);
    }
}
