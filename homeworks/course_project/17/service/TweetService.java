package com.firmata.elon.service;

import com.firmata.elon.model.Tweet;
import com.firmata.elon.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TweetService {
    private final TweetRepository tweetRepository;

    @Autowired
    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public Tweet createTweet(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    public List<Tweet> fetchAllTweet() {
        return tweetRepository.findAll();
    }

    public List<Tweet> fetchAllByUser(int id) {
        return tweetRepository.findByUserId(id);
    }

    public Optional<Tweet> fetchTweet(int id) {
        return tweetRepository.findById(id);
    }

    public Optional<Tweet> updateTweet(int id, Tweet tweet) {
        Optional<Tweet> selectedTweet = fetchTweet(id);

        if (selectedTweet.isPresent()) {
            Tweet newTweet = selectedTweet.get();
            newTweet.setText(tweet.getText());
            tweetRepository.save(newTweet);
        }
        return selectedTweet;
    }

    public String deleteTweet(int id) {
        tweetRepository.deleteById(id);
        return "Tweet deleted";
    }

}
