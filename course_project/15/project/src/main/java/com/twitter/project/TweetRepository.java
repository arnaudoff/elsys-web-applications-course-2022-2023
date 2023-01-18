package com.twitter.project;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TweetRepository {

    final JdbcTemplate jdbcTemplate;

    public TweetRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTweet(Tweet tweet){
        jdbcTemplate.update("insert into tweet (tweetText, tweetDate, authorId) values(?,?,?)",
                            tweet.getTweetText(),tweet.getTweetDate(),tweet.getAuthorId());
    }

    public Optional<Tweet> findById(int id) {
        String sql = "SELECT * FROM tweets WHERE id = ?";
        try {
            Tweet tweet = jdbcTemplate.queryForObject(sql, new Object[] {id}, BeanPropertyRowMapper.newInstance(Tweet.class));
            return Optional.ofNullable(tweet);
        } catch (DataAccessException e) {
            return null;
        }
    }

    public List<Tweet> findByAuthor(int authorId) {
        String sql = "SELECT * FROM tweets WHERE user_id = ?";
        try {
            List<Tweet> tweets = jdbcTemplate.query(sql, new Object[] {authorId},
                    BeanPropertyRowMapper.newInstance(Tweet.class));
            return tweets;
        } catch (DataAccessException e) {
            return null;
        }
    }

    public void deleteTweet(int id) {
        jdbcTemplate.update("Delete from tweet where id = ?;",id);
    }

    public void updateTweet(int id, String tweetText) {
        jdbcTemplate.update("UPDATE tweet set tweetText = ? where id = ?", new Object[] {tweetText, id});
    }
}
