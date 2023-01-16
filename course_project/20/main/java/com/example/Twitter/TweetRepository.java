package com.example.Twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class TweetRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addTweet(String author, String text ){
        Tweet newTweet = new Tweet(author,text);
        jdbcTemplate.update("INSERT INTO twitterhomework.tweets(author, tweetText, tweetDate) VALUES (?,?,?)",
                newTweet.getAuthor(),newTweet.getTweetText(), Date.valueOf(newTweet.getTweetDate()));

    }


    public Tweet getTweet(int id) {
        String sql = "SELECT * FROM tweets WHERE ID = ?";
        return (Tweet) jdbcTemplate.queryForObject(
                sql,
                (RowMapper) (rs,rowNum)->new Tweet(
                        rs.getInt("id"),
                        rs.getString("author"),
                        rs.getString("tweetText"),
                        rs.getDate("tweetDate").toLocalDate()
                ),
                new Object[]{id}
        );
    }

    public void deleteTweet(int id) {
        jdbcTemplate.update("Delete from tweets where id = ?;",id);
    }

    public List<Tweet> getUserTweets(int id) {

        List<Tweet>  queryUsers = jdbcTemplate.query(
                "SELECT * FROM twitterhomework.tweets LEFT JOIN  twitterhomework.users ON tweets.author = users.username where users.id = ? ORDER BY tweets.tweetDate DESC;",
                (RowMapper) (rs, rowNum) ->
                     new Tweet(
                            rs.getInt("id"),
                            rs.getString("author"),
                            rs.getString("tweetText"),
                            rs.getDate("tweetDate").toLocalDate()
                ),

                id);
        return queryUsers;
    }

    public void updateTweet(int id, String tweetText) {
        jdbcTemplate.update("UPDATE tweets set tweetText = ? where id = ?", tweetText, id);
    }
}
