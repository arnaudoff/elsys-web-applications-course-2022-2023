package com.example.twitterapi;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TweetController
{
	@GetMapping("/tweets/{id}")
	Tweet getTweet(@PathVariable long id)
	{
		if (Tweet.tweets.containsKey(id))
		{
			return Tweet.tweets.get(id);
		}
		else
		{
			throw new TweetNotFoundException(id);
		}
	}

	@PostMapping("/tweets")
	Tweet newTweet(@RequestBody long author, @RequestBody String text)
	{
		if (!User.usersId.containsKey(author))
		{
			throw new UserNotFoundException(author);
		}
		else
		{
			return new Tweet(author, text);
		}
	}

	@GetMapping("/tweets/by-user/{id}")
	List<Tweet> getUserTweets(@PathVariable long id)
	{
		List<Tweet> tweets = new ArrayList<>(User.usersId.get(id).tweets.values());
		tweets.sort(new TweetDateSortDesc());
		return tweets;
	}

	@DeleteMapping("/tweets/{id}")
	Tweet deleteTweet(@PathVariable long id)
	{
		Tweet tweet = Tweet.tweets.get(id);
		User.usersId.get(tweet.author).tweets.remove(id);
		Tweet.tweets.remove(id);
		return tweet;
	}

	@PutMapping("/tweets/{id}")
	Tweet changeTweet(@PathVariable long id, @RequestBody String text)
	{
		Tweet tweet = Tweet.tweets.get(id);
		tweet.text = text;
		return tweet;
	}
}
