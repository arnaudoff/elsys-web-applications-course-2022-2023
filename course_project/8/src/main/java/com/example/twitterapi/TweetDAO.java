package com.example.twitterapi;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class TweetDAO
{
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource)
	{
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public JdbcTemplate getJdbcTemplate()
	{
		return jdbcTemplate;
	}

	public List<Tweet> getAllTweets()
	{
		String query = "select * from Tweet";
		return jdbcTemplate.query(query, new TweetMapper());
	}

	public int addTweet(Tweet tweet)
	{
		String query = "insert into Tweet values(?, ?, ?, ?)";
		return jdbcTemplate.update(query,
		                           tweet.author,
		                           tweet.content,
		                           tweet.postDate,
		                           tweet.id);
	}

	public int deleteTweet(Tweet tweet)
	{
		String query = "delete from Tweet where id = ?";
		return jdbcTemplate.update(query, tweet.id);
	}

	public int changeTweet(Tweet tweet)
	{
		String query = "update Tweet set content = ? where id = ?";
		return jdbcTemplate.update(query, tweet.content, tweet.id);
	}
}
