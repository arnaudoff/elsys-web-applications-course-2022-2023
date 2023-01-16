package com.example.Twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    public List<User> getAllUsers() {
        List<User>  queryUsers = jdbcTemplate.query(
                "SELECT * FROM twitterhomework.users ;",
                (rs, rowNum) -> {
                    User user = new User(
                             rs.getInt("id"),
                            rs.getString("username"),
                            rs.getDate("registrationDate").toLocalDate(),
                            rs.getInt("followersCount"),
                            rs.getInt("followingCount"));
                    return user;
                }
        );
        return queryUsers;
    }

    public void addUser(String username) throws SQLException {
        User newUser = new User(username);

        jdbcTemplate.update("INSERT INTO twitterhomework.users(username,registrationDate, followersCount, followingCount) VALUES(?,?,?,?)",
                newUser.getUsername(), Date.valueOf(newUser.getRegidtrationDate()), newUser.getFollowersCount(),newUser.getFollowingCount());
    }

    public User getUser(int id) {
        String sql = "SELECT * FROM users WHERE ID = ?";
        return (User) jdbcTemplate.queryForObject(
                sql,
                (RowMapper) (rs, rowNum) -> new User (
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getDate("registrationDate").toLocalDate(),
                        rs.getInt("followersCount"),
                        rs.getInt("followingCount")),
                new Object[]{id}
        );
    }
}

