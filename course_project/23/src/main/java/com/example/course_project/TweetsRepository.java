package com.example.course_project;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class TweetsRepository extends ConnectionRepository {
    public List<Tweet> getTweets() {
        var tweets = new LinkedList<Tweet>();
        try (var statement = connection.prepareStatement("select * from tweets;")) {
            var result = statement.executeQuery();
            while (result.next()) {
                tweets.add(new Tweet(result.getLong("id"), result.getDate("date"),
                        result.getLong("author"), result.getString("text")));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return tweets;
    }

    public Optional<Tweet> getTweet(long id) {
        try (var statement = connection.prepareStatement("select * from tweets where id = ?;")) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            if (result.next()) {
                return Optional.of(new Tweet(result.getLong("id"), result.getDate("date"),
                        result.getLong("author"), result.getString("text")));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    public void addTweet(long userId, String text) {
        try (var statement = connection.prepareStatement("insert into tweets(author, text) values(?, ?);")) {
            statement.setLong(1, userId);
            statement.setString(2, text);
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<Tweet> getTweetsByUser(long userId) {
        var tweets = new LinkedList<Tweet>();
        try (var statement = connection.prepareStatement("select * from tweets where author = ?;")) {
            statement.setLong(1, userId);
            var result = statement.executeQuery();
            while (result.next()) {
                tweets.add(new Tweet(result.getLong("id"), result.getDate("date"),
                        result.getLong("author"), result.getString("text")));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return tweets;
    }

    public void removeTweet(long id) {
        try (var statement = connection.prepareStatement("delete from tweets where id = ?;")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateTweet(long id, String text) {
        try (var statement = connection.prepareStatement("update tweets set text = ? where id = ?;")) {
            statement.setString(1, text);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
