package com.twitter.twitterapplication;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TweetRepository {

    final NamedParameterJdbcTemplate namedJDBCtemplate;

    public TweetRepository(NamedParameterJdbcTemplate namedJDBCtemplate) {
        this.namedJDBCtemplate = namedJDBCtemplate;
    }

    public void createTweet(Tweet tweet) {
        String sql = "INSERT INTO tweets (text, date_created, author_id) VALUES (:text, :date_created, :author_id)";
        Map<String,Object> map = new HashMap<>();
        map.put("text",tweet.getText());
        map.put("date_created",tweet.getDateCreated());
        map.put("author_id", tweet.getAuthorId());
        namedJDBCtemplate.execute(sql, map, (PreparedStatementCallback) ps -> ps.executeUpdate());
    }

    public Tweet findById(int id) {
        String sql = "SELECT * FROM tweets WHERE id = :id";
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        try {
            Tweet tweet =  namedJDBCtemplate.queryForObject(sql, map,
                    BeanPropertyRowMapper.newInstance(Tweet.class));
            return tweet;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public ArrayList<Tweet> findByAuthor(int author_id) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        String sql = "SELECT * FROM tweets WHERE author_id = :author_id";
        Map<String,Object> map = new HashMap<>();
        map.put("author_id",author_id);
        try {
            tweets = (ArrayList<Tweet>) namedJDBCtemplate.query(sql, map,
                    BeanPropertyRowMapper.newInstance(Tweet.class));
            return tweets;
        } catch (EmptyResultDataAccessException e) {
            return tweets;
        }
    }

    public void deleteTweet(int id) {
        String sql = "DELETE FROM tweets WHERE id= :id";
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        namedJDBCtemplate.execute(sql, map, (PreparedStatementCallback) ps -> ps.executeUpdate() );
    }

    public void changeTweet(int id, String text) {
        String sql = "UPDATE tweets SET text=:text WHERE id=:id";
        Map<String,Object> map = new HashMap<>();
        map.put("text", text);
        map.put("id",id);
        namedJDBCtemplate.execute(sql, map, (PreparedStatementCallback) ps -> ps.executeUpdate());
    }
}
