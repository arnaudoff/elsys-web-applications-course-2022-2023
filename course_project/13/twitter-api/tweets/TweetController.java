package org.elsysbg.twitterapi.tweets;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tweets")
public class TweetController {
    @Autowired
    private TweetService tweetService;
    
    @PostMapping
    public void postTweet(@RequestBody TweetRequest tweetRequest) {
        tweetService.postTweet(tweetRequest);
    }
    
    @GetMapping("/{id}")
    public Optional<Tweet> fetchTweetById(@PathVariable long id) {
        return tweetService.fetchFromId(id);
    }
    
    @GetMapping("/by-user/{author_id}")
    public List<Tweet> fetchTweetsByAuthor(@PathVariable long author_id) {
        return tweetService.fetchFromAuthorId(author_id);
    }
    
    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable long id) {
        tweetService.deleteTweet(id);
    }
    
    @PutMapping("/{id}")
    public void updateTweet(@PathVariable long id, @RequestBody TweetRequest tweetRequest) {
        tweetService.updateTweet(id, tweetRequest.getText());
    }

}
