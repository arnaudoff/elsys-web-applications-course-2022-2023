package com.example.twitterapi;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.List;

public class UserDAO
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

	public List<User> getAllUsers()
	{
		String query = "select * from User";
		return jdbcTemplate.query(query, new UserMapper());
	}

	public int addUser(User user)
	{
		String query = "insert into User values(?, ?, ?, ?, ?)";
		return jdbcTemplate.update(query,
		                           user.username,
		                           user.registrationDate,
		                           user.followerCount,
		                           user.followingCount,
		                           user.id);
	}
}
