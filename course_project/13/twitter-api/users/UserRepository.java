package org.elsysbg.twitterapi.users;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public void insert(User user) {
        jdbcTemplate.update(
            "INSERT INTO users (username, registration_date, followers_count, following_count) VALUES (?, ?, ?, ?)",
            user.getUsername(),
            user.getRegistrationDate(),
            user.getFollowersCount(),
            user.getFollowingCount()
        );
    }
    
    public Optional<User> fetch(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = ?",
                new Object[] {id},
                BeanPropertyRowMapper.newInstance(User.class)));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
