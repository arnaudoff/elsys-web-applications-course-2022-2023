package com.example.twitter.users;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UsersRepository {

    final
    JdbcTemplate jdbcTemplate;

    public UsersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void register(User user){
        String sql = "INSERT INTO users (username, date_registered, followers_count, following_count) VALUES (?, ?, ?, ?)";
        Object[] params = {user.getUsername(), user.getDate_registered(), user.getFollowers_count(), user.getFollowing_count()};
        jdbcTemplate.update(sql, params);
    }
    public Optional<User> findById(int id){
        String sql = "SELECT * FROM users WHERE id = ?";
        Object[] params = {id};
        try {
            User user =  jdbcTemplate.queryForObject(sql, params,
                   BeanPropertyRowMapper.newInstance(User.class));
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
