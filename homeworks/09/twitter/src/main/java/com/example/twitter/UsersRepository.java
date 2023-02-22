package com.example.twitter;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
}
