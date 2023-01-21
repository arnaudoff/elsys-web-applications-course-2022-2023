package com.example.course_project;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UsersRepository extends ConnectionRepository {
    public void addUser(String username) {
        try (var statement = connection.prepareStatement("insert into users(username) values(?);")) {
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Optional<User> getUser(long id) {
        try (var statement = connection.prepareStatement("select * from users where id = ?;")) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next()) {
                return Optional.of(new User(result.getLong("id"), result.getString("username"),
                        result.getDate("registrationDate"), result.getLong("followers"), result.getLong("following")));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }
}
