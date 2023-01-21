package com.example.elonmuskhw.repositoryies;

import com.example.elonmuskhw.DTOs.TweetUpdateRequest;
import com.example.elonmuskhw.models.Tweet;
import com.example.elonmuskhw.models.User;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TweetRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public void insertTweet(Tweet tweet) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(tweet);
        session.getTransaction().commit();
        session.close();
    }

    public User getUserById(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public Tweet getTweet(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Tweet tweet = session.get(Tweet.class, id);
        session.getTransaction().commit();
        session.close();
        return tweet;
    }

    public List<Tweet> getTweets(Integer userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Tweet> tweets = session.get(User.class, userId).getTweets();
        session.getTransaction().commit();
        session.close();
        return tweets;
    }

    @Transactional
    public void deleteTweet(Integer tweetId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Tweet tweet = session.get(Tweet.class, tweetId);
        session.remove(tweet);
        session.getTransaction().commit();
        session.close();
    }

    public void updateTweet(Integer tweetId, TweetUpdateRequest tweetUpdateRequest) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Tweet tweet = session.get(Tweet.class, tweetId);
        tweet.setText(tweetUpdateRequest.getText());
        session.merge(tweet);
        session.flush();
        session.getTransaction().commit();
        session.close();
    }
}
