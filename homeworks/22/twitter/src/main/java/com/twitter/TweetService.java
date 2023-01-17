package com.twitter;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    public Tweet createTweet(String authorUsername, String text) {
        User author = userRepository.findByUsername(authorUsername);
        if (author == null) {
            throw new IllegalArgumentException("Invalid author");
        }
        Tweet tweet = new Tweet();
        tweet.setAuthor(author);
        tweet.setText(text);
        return tweetRepository.save(tweet);
    }

    public Tweet getTweet(Long id) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isEmpty()) {
            throw new TweetNotFoundException(id);
        }
        return tweet.get();
    }

    public List<Tweet> getTweetsByUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return tweetRepository.findByAuthorOrderByCreatedAtDesc(user.get());
    }

    public void deleteTweet(Long id) {
        Optional<Tweet> tweet = tweetRepository.findById(id);
        if (tweet.isEmpty()) {
            throw new TweetNotFoundException(id);
        }
        tweetRepository.deleteById(id);
    }

    public Tweet updateTweet(Long id, Tweet tweet) {
        Optional<Tweet> tweetOptional = tweetRepository.findById(id);
        if (tweetOptional.isEmpty()) {
            throw new TweetNotFoundException(id);
        }
        Tweet tweetToUpdate = tweetOptional.get();
        tweetToUpdate.setText(tweet.getText());
        return tweetRepository.save(tweetToUpdate);
    }

}