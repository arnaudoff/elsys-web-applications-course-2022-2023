package org.elsys_bg.spring_hw.tweet;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TweetRepository extends JpaRepository<Tweet, Integer> {
	
}
