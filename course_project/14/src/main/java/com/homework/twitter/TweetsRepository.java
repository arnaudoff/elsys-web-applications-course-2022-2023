package com.homework.twitter;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TweetsRepository extends CrudRepository<Tweet, Long> {
    Optional<Tweet> findById(long id);

}
