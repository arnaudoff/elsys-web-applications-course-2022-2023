package com.example.twitterapi;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class AppConfig
{
	@Bean
	@Primary
	@ConfigurationProperties ("app.datasource")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties("app.datasource.configuration")
	public DataSource dataSource()
	{
		return DataSourceBuilder.create().build();
	}

	@Bean
	public UserDAO userDAO(DataSource dataSource)
	{
		UserDAO userDAO = new UserDAO();
		userDAO.setDataSource(dataSource);
		return userDAO;
	}

	@Bean
	public TweetDAO tweetDAO(DataSource dataSource)
	{
		TweetDAO tweetDAO = new TweetDAO();
		tweetDAO.setDataSource(dataSource);
		return tweetDAO;
	}
}
