package com.homework.twitter;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    Optional<User> findById(long id);

}
