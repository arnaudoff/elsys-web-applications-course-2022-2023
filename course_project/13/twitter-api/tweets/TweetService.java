package org.elsysbg.twitterapi.tweets;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TweetService {
    @Autowired
    private TweetRepository tweetRepository;
    
    public void postTweet(TweetRequest tweetRequest) {
        tweetRepository.insert(new Tweet(
            0,  // db assigns id
            tweetRequest.getAuthorId(),
            tweetRequest.getText(),
            LocalDate.now()
        ));
    }
    
    public Optional<Tweet> fetchFromId(long id) {
        return tweetRepository.fetchById(id);
    }

    public List<Tweet> fetchFromAuthorId(long id) {
        return tweetRepository.fetchByAuthorId(id);
    }
    
    public void deleteTweet(long id) {
        tweetRepository.delete(id);
    }
    
    public void updateTweet(long id, String text) {
        tweetRepository.update(id, text);
    }
}
