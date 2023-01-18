package org.elsys_bg.spring_hw.tweet;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tweets")
public class TweetController {

	@Autowired
	private TweetRepository tweetRepository;

	@GetMapping("/{id}")
	public Optional<Tweet> getTweet(@PathVariable int id) {
		return tweetRepository.findById(id);
	}

	@GetMapping("/by-user/{id}")
	public List<Tweet> getTweetsByUser(@PathVariable int id) {
		return tweetRepository.findAll().stream()
			.filter(t -> t.getAuthorId() == id)
			.collect(Collectors.toList());
	}

	@PostMapping
	public void postTweet(@RequestBody PostTweetRequest request) {
		tweetRepository.save(new Tweet(request.getAuthorId(), request.getText()));
	}

	@DeleteMapping("/{id}")
	public void deleteTweet(@PathVariable int id) {
		tweetRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public void updateTweet(@PathVariable int id, @RequestBody UpdateTweetRequest request) {
		Optional<Tweet> tweet = tweetRepository.findById(id);
		
		if (tweet.isPresent()) {
			tweet.get().updateTweet(request.getText());
			tweetRepository.save(tweet.get());
		}
	}
}
