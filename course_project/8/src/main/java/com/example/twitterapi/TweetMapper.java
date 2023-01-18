package com.example.twitterapi;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TweetMapper implements RowMapper<Tweet>
{
	@Override
	public Tweet mapRow (ResultSet rs, int rowNum) throws SQLException
	{
		Tweet tweet = new Tweet();
		tweet.author = rs.getLong("author");
		tweet.content = rs.getString("content");
		tweet.postDate = rs.getDate("postDate");
		tweet.id = rs.getLong("id");

		return tweet;
	}
}
