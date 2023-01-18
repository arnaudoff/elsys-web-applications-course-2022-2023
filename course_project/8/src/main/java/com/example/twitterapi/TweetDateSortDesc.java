package com.example.twitterapi;

import java.util.Comparator;

public class TweetDateSortDesc implements Comparator<Tweet>
{

	@Override
	public int compare (Tweet o1, Tweet o2)
	{
		return o2.postDate.compareTo(o1.postDate);
	}
}
