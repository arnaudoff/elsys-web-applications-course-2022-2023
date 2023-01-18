package com.example.twitterapi;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User>
{
	@Override
	public User mapRow (ResultSet rs, int rowNum) throws SQLException
	{
		User user = new User();
		user.username = rs.getString("username");
		user.registrationDate = rs.getDate("registrationDate");
		user.followerCount = rs.getLong("followerCount");
		user.followingCount = rs.getLong("followingCount");
		user.id = rs.getLong("id");

		return user;
	}
}
