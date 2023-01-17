package com.example.twitterapi;

import java.util.List;

import static java.lang.Thread.sleep;

public class Main
{
	public static void main(String[] args) throws InterruptedException
	{
		User user = new User("Test User");
		Tweet tweet1 = new Tweet(user.id, "Test Tweet 1");
		sleep(1);
		Tweet tweet2 = new Tweet(user.id, "Test Tweet 2");
		sleep(2);
		Tweet tweet3 = new Tweet(user.id, "Test Tweet 3");
		sleep(5);

		List<Tweet> tweets = new java.util.ArrayList<>(Tweet.tweets.values());
		tweets.sort(new TweetDateSortDesc());

		for (Tweet i : tweets)
		{
			System.out.println(i.text);
		}
	}
}
