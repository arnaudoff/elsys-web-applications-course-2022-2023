package com.homework.twitter;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TweetService {
    private final TweetsRepository tweetsRepository;
    private final UserService userService;

    public TweetService(TweetsRepository tweetsRepository, UserService userService) {
        this.tweetsRepository = tweetsRepository;
        this.userService = userService;
    }

    public GetTweetResponse getTweet(long id) {
        Optional<Tweet> optionalTweet = tweetsRepository.findById(id);
        if (optionalTweet.isPresent()) {
            Tweet response = optionalTweet.get();
            GetTweetResponse tweet = new GetTweetResponse();
            tweet.setId(id);
            tweet.setDate(response.getDate());
            tweet.setText(response.getText());
            tweet.setAuthor(userService.getUser(response.getAuthor().getId()));

            return tweet;
        } else {
            try {
                throw new Exception("Tweet with id " + id + " not found");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Tweet addTweet(AddTweetRequest request) {
        Tweet tweet = new Tweet();
        tweet.setAuthor(getUserFromTweetRequest(request));
        tweet.setText(request.getText());
        tweet.setDate(LocalDateTime.now());
        return tweetsRepository.save(tweet);
    }

    public void updateTweet(long id, UpdateTweetRequest request) {
        Optional<Tweet> optionalTweet = tweetsRepository.findById(id);
        if (optionalTweet.isPresent()) {
            Tweet tweet = optionalTweet.get();
            tweet.setText(request.getText());
            tweetsRepository.save(tweet);
        } else {
            throw new RuntimeException("Tweet with id " + id + " not found");
        }
    }

    public void deleteTweet(long id) {
        tweetsRepository.deleteById(id);
    }

    public User getUserFromTweetRequest(AddTweetRequest request) {
        User user = new User();
        GetUserResponse response = userService.getUser(request.getAuthorId());
        user.setId(response.getId());
        user.setUsername(response.getUsername());
        user.setRegistrationDate(response.getRegistrationDate());
        user.setFollowersCount(response.getFollowersCount());
        user.setFollowingCount(response.getFollowingCount());
        return user;
    }

    public List<GetTweetResponse> getTweetsByUserId(long id) {
        List<Tweet> tweets = (ArrayList<Tweet>) tweetsRepository.findAll();
        List<GetTweetResponse> responses = new ArrayList<>();
        return tweets.stream()
                .filter(tweet -> tweet.getAuthor().getId() == id)
                .map(tweet -> {
                    GetTweetResponse response = new GetTweetResponse();
                    response.setId(tweet.getId());
                    response.setDate(tweet.getDate());
                    response.setText(tweet.getText());
                    response.setAuthor(userService.getUser(tweet.getAuthor().getId()));
                    return response;
                })
                .sorted(Comparator.comparing(GetTweetResponse::getDate).reversed())
                .collect(Collectors.toList());
    }

}
