package com.twitter.project;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TweetService {

    private  TweetRepository tweetRepository;

    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public void addTweet(AddTweetRequest addTweetRequest){
        Tweet tweet = new Tweet();
        tweet.setTweetText(addTweetRequest.getTweetText());
        tweet.setTweetDate(LocalDateTime.now());
        tweet.setAuthorId(addTweetRequest.getAuthorId());
    }

    public Optional<Tweet> getTweet(int id){
        return tweetRepository.findById(id);
    }

    public List<Tweet> getTweetByAuthor(int authorId){
        return tweetRepository.findByAuthor(authorId);
    }

    public void deleteTweet(int id){
        tweetRepository.deleteTweet(id);
    }

    public void updateTweet(int id, String tweetText){
        tweetRepository.updateTweet(id, tweetText);
    }
}