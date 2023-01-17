package com.example.twitterapi;

import java.security.SecureRandom;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class User
{
	String username;
	Date registrationDate;
	long followerCount;
	long followingCount;

	Map<Long, Tweet> tweets;

	long id;

	static Map<Long, User> usersId = new LinkedHashMap<>();
	static Map<String, User> usersName = new LinkedHashMap<>();

	public User (String username)
	{
		if (usersName.containsKey(username))
		{
			throw new IllegalArgumentException("Username already exists");
		}

		long followerRandom = 20;
		long followingRandom = 20;

		// x^3 + rand((x+1)^3 - x^3)
		Random random = new Random();
		long followerCount = random.nextLong(followerRandom);
		followerCount = followerCount * followerCount * followerCount
		                + random.nextLong(3 * followerCount * followerCount + 3 * followerCount + 1);
		long followingCount = random.nextLong(followingRandom);
		followingCount = followingCount * followingCount * followingCount
		                 + random.nextLong(3 * followingCount * followingCount + 3 * followingCount + 1);

		this.username = username;
		this.followerCount = followerCount;
		this.followingCount = followingCount;

		this.tweets = new LinkedHashMap<>();

		SecureRandom secureRandom = new SecureRandom();
		long id = secureRandom.nextLong();
		while (tweets.containsKey(id))
		{
			id = secureRandom.nextLong();
		}

		this.id = id;

		this.registrationDate = new Date();

		usersId.put(this.id, this);
		usersName.put(this.username, this);
	}
}
