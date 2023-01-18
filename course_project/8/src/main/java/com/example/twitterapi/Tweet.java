package com.example.twitterapi;

import java.security.SecureRandom;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Tweet
{
	long author;
	String content;
	Date postDate;

	long id;

	static Map<Long, Tweet> tweets = new LinkedHashMap<>();

	public Tweet() {}

	public Tweet (long author, String content)
	{
		if (!User.usersId.containsKey(author))
		{
			throw new IllegalArgumentException("Tweet author doesn't exist");
		}
		if (content.length() > 281)
		{
			throw new IllegalArgumentException("Tweet content is too long");
		}

		this.author = author;
		this.content = content;

		SecureRandom secureRandom = new SecureRandom();
		long id = secureRandom.nextLong();
		while (tweets.containsKey(id))
		{
			id = secureRandom.nextLong();
		}

		this.id = id;

		this.postDate = new Date();

		tweets.put(this.id, this);
		User.usersId.get(this.author).tweets.put(this.id, this);
	}

	@Override
	public String toString ()
	{
		return "Tweet{" + "author=" + author + ", text='" + content + '\'' + ", date=" + postDate + ", id=" + id + '}';
	}
}
