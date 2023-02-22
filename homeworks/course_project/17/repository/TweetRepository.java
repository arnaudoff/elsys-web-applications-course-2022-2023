package com.firmata.elon.repository;

import com.firmata.elon.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Integer> {

    List<Tweet> findByUserId(int user_id);
}
