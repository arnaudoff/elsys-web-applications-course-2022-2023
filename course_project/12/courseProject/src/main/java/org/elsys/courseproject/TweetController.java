package org.elsys.courseproject;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tweets")
public class TweetController {
    private TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping
    public void addTweet(@RequestBody AddTweetRequest addTweetRequest) {
        tweetService.addTweet(addTweetRequest);
    }

    @GetMapping("/{id}")
    public Tweet getTweet(@PathVariable String id) {
        return tweetService.getTweet(id);
    }

    @GetMapping
    public List<Tweet> getAllTweets(){
        return tweetService.getAllTweets();
    }

    @GetMapping("/by-user/{id}")
    public List<Tweet> getTweetsByUserId(@PathVariable String id) {
        return tweetService.getTweetsByUserId(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable String id){
        tweetService.deleteTweet(id);
    }

    @PutMapping("/{id}")
    public void updateTweet(@PathVariable String id, @RequestBody UpdateTweetRequest updateTweetRequest) {
        tweetService.updateTweet(id, updateTweetRequest);
    }
}
