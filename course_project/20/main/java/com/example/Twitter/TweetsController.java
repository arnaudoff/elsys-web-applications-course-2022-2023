package com.example.Twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TweetsController {

    @Autowired
    TweetRepository tweetRepository;


    @RequestMapping("/tweets/{id}")
    public Tweet getTweet(@PathVariable int id ){
        return tweetRepository.getTweet(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tweets")
    public void addTweet( @RequestBody Map<String,String> newTweet){
        System.out.println("Author = " + newTweet.get("author"));
        System.out.println("TweetText = " + newTweet.get("tweetText"));

        tweetRepository.addTweet(newTweet.get("author"),newTweet.get("tweetText"));
    }



    @RequestMapping(method = RequestMethod.PUT, value = "/tweets/{id}")
    public void updateTweet(@PathVariable int id,@RequestBody Map<String,String> newTweet){
        System.out.println("Author = " + id );
        System.out.println("TweetText = " + newTweet.get("tweetText"));

        tweetRepository.updateTweet(id,newTweet.get("tweetText"));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/tweets/{id}")
    public void deleteTweet(@PathVariable int id ){
        tweetRepository.deleteTweet(id);
    }

    @RequestMapping("tweets/by-user/{id}")
    public List<Tweet> getUserTweets(@PathVariable int id){
        return tweetRepository.getUserTweets(id);
    }
}
