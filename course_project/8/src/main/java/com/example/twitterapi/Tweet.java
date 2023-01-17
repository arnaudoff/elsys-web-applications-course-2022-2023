package com.example.twitterapi;

import java.security.SecureRandom;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Tweet
{
	long author;
	String text;
	Date date;

	long id;

	static Map<Long, Tweet> tweets = new LinkedHashMap<>();

	public Tweet (long author, String text)
	{
		if (!User.usersId.containsKey(author))
		{
			throw new IllegalArgumentException("Tweet author doesn't exist");
		}

		this.author = author;
		this.text = text;

		SecureRandom secureRandom = new SecureRandom();
		long id = secureRandom.nextLong();
		while (tweets.containsKey(id))
		{
			id = secureRandom.nextLong();
		}

		this.id = id;

		this.date = new Date();

		tweets.put(this.id, this);
		User.usersId.get(this.author).tweets.put(this.id, this);
	}
}
