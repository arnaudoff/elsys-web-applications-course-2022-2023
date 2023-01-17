package com.example.course_project;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TweetsService {
    private TweetsRepository tweetsRepository;

    public TweetsService(TweetsRepository tweetsRepository) {
        this.tweetsRepository = tweetsRepository;
    }

    public List<Tweet> getTweets() {
        return tweetsRepository.getTweets();
    }

    public Optional<Tweet> getTweetById(long id) {
        return tweetsRepository.getTweetById(id);
    }

    public void addTweet(Tweet newTweet) {
        tweetsRepository.addTweet(newTweet);
    }

    public void removeTweet(long id) {
        tweetsRepository.removeTweet(id);
    }

    public void updateTweetText(long id, String newText) {
        tweetsRepository.updateTweetText(id, newText);
    }
}
