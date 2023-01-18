package com.twitter.project;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUser(User user){
        jdbcTemplate.update("insert into user (username, registrationDate, followersCount, followingCount) values(?,?,?, ?)",
                user.getUsername(), user.getRegistrationDate(), user.getFollowersCount(), user.getFollowingCount());
    }

    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM tweets WHERE id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[] {id}, BeanPropertyRowMapper.newInstance(User.class));
            return Optional.ofNullable(user);
        } catch (DataAccessException e) {
            return null;
        }
    }
}
