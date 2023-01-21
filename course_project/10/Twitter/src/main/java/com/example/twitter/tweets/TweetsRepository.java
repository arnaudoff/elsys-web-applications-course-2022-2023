package com.example.twitter.tweets;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class TweetsRepository {

    final JdbcTemplate jdbcTemplate;

    public TweetsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTweet(Tweet tweet) {
        String sql = "INSERT INTO tweets (text, date_created, user_id) VALUES (?, ?, ?)";
        Object[] params = {tweet.getText(), tweet.getDate_created(), tweet.getUser_id()};
        jdbcTemplate.update(sql, params);
    }

    public Optional<Tweet> findById(int id) {
        String sql = "SELECT * FROM tweets WHERE id = ?";
        Object[] params = {id};
        try {
            Tweet tweet =  jdbcTemplate.queryForObject(sql, params,
                    BeanPropertyRowMapper.newInstance(Tweet.class));
            return Optional.ofNullable(tweet);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Tweet> findByAuthor(int user_id) {
        String sql = "SELECT * FROM tweets WHERE user_id = ?";
        Object[] params = {user_id};
        try {
            List<Tweet> tweets = jdbcTemplate.query(sql, params,
                    BeanPropertyRowMapper.newInstance(Tweet.class));
            return tweets;
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }

    public void deleteTweet(int id) {
        String sql = "DELETE FROM tweets WHERE id=?";
        Object[] params = {id};
        jdbcTemplate.update(sql, params);
    }

    public void changeTweet(int id, String text) {
        String sql = "UPDATE tweets SET text=? WHERE id=?";
        Object[] params = {text, id};
        jdbcTemplate.update(sql, params);
    }
}
