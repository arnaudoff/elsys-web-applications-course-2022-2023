package org.elsys.courseproject;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {
    private TweetRepository tweetRepository;

    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public void addTweet(AddTweetRequest addTweetRequest) {
        Tweet tweet = new Tweet(addTweetRequest.getUserId(), addTweetRequest.getText());
        tweetRepository.insertTweet(tweet);
    }

    public Tweet getTweet(String Id) {
        return tweetRepository.getTweet(Id);
    }

    public List<Tweet> getAllTweets() {
        return tweetRepository.getAllTweets();
    }

    public List<Tweet> getTweetsByUserId(String id) {
        return tweetRepository.getTweetsByUserId(id);
    }

    public void deleteTweet(String id) {
        tweetRepository.deleteTweet(id);
    }

    public void updateTweet(String id, UpdateTweetRequest updateTweetRequest) {
        tweetRepository.updateTweet(id, updateTweetRequest.getText());
    }
}
