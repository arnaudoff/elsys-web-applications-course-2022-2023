package com.twitter.twitterapplication;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    final NamedParameterJdbcTemplate namedJDBCTemplate;

    public UserRepository(NamedParameterJdbcTemplate namedJDBCTemplate) {
        this.namedJDBCTemplate = namedJDBCTemplate;
    }

    /*
    POST /users - registers a user (only by username for simplicity) - done
    GET /users/{id} - returns details about a specific user
     */

    public void registerUser(User user){
        String sql = "INSERT INTO users (username, date_registered, followers_count, following_count) VALUES (:username, :date_registered, :followers_count, :following_count)";
        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("date_registered", user.getDateRegistered());
        map.put("followers_count", user.getFollowersCount());
        map.put("following_count", user.getFollowingCount());

        namedJDBCTemplate.execute(sql, map, (PreparedStatementCallback) ps -> ps.executeUpdate());
    }

    public User getUserById(int id){
        String sql = "SELECT * FROM users WHERE id = :id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);

        try{
            return namedJDBCTemplate.queryForObject(sql, map, BeanPropertyRowMapper.newInstance(User.class));
        }catch (Exception e){
            return null;
        }
    }

}
