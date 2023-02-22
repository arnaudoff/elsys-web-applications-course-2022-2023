package com.example.homework4;


import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TweetsRepository {
    private List<Tweet> tweets = new ArrayList<>();

    public TweetsRepository() {
    }

    public void createTweet(Tweet tweet) {
        tweets.add(tweet);
    }

    public Optional<Tweet> findById(int id) {
        for (Tweet tweet : tweets) {
            if (tweet.getId() == id) {
                return Optional.of(tweet);
            }
        }
        return Optional.empty();
    }

    public List<Tweet> findByAuthor(int user_id) {
        List<Tweet> tweetsByAuthor = new ArrayList<>();
        for (Tweet tweet : tweets) {
            System.out.printf("tweet.getUser_id() = %d", tweet.getUserId());
            if (tweet.getUserId() == user_id) {
                tweetsByAuthor.add(tweet);
            }
        }
//        tweetsByAuthor.stream().forEach(System.out::println);
        return tweetsByAuthor;
    }

    public void deleteTweet(int id) {
        tweets.removeIf(tweet -> tweet.getId() == id);
    }

    public void changeTweet(int id, String text) {
        for (Tweet tweet : tweets) {
            if (tweet.getId() == id) {
                tweet.setText(text);
            }
        }
    }
}