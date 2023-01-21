package org.elsysbg.twitterapi.tweets;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TweetRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void insert(Tweet tweet) {
        jdbcTemplate.update(
            "INSERT INTO tweets (author_id, text, date) VALUES (?, ?, ?)",
            tweet.getAuthorId(),
            tweet.getText(),
            LocalTime.now()
        );
    }
    
    public Optional<Tweet> fetchById(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM tweets WHERE id = ?",
                new Object[] {id},
                BeanPropertyRowMapper.newInstance(Tweet.class)));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Tweet> fetchByAuthorId(long authorId) {
        try {
            return jdbcTemplate.query(
            "SELECT * FROM tweets WHERE author_id = ?",
                new Object[] {authorId},
                BeanPropertyRowMapper.newInstance(Tweet.class));
        } catch (DataAccessException e) {
            return List.of();
        }
    }
    
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM tweets WHERE id = ?", id);
    }
    
    public void update(long id, String text) {
        jdbcTemplate.update("UPDATE tweets SET text = ? WHERE id = ?", text, id);
    }
}
