package com.example.instagramapihomework2;

import Models.Account;
import Models.Comment;
import Models.Like;
import Models.Post;

import java.sql.*;
import java.util.ArrayList;

public final class DAO {
    private static Connection initializeConnection()  {
        String dbDriver = "com.mysql.cj.jdbc.Driver";
        String dbURL = "jdbc:mysql:// localhost:3306/";
        // Database name to access
        String dbName = "instagram";
        String dbUsername = "root";
        String dbPassword = "123456y";

        try {
            Class.forName(dbDriver);
            return DriverManager.getConnection(dbURL + dbName,
                dbUsername,
                dbPassword);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addAccount(Account account) throws SQLException {
        Connection con = initializeConnection();
        String query = String.format("insert into account (first_name, last_name, username, password) values('%s', '%s', '%s', '%s')", account.getFirst_name(), account.getLast_name(), account.getUsername(), account.getPassword());
        Statement statement = con.createStatement();

        statement.executeUpdate(query);
        // MAYBE USE: https://stackoverflow.com/questions/13434714/jdbc-find-out-if-query-was-successful ???
    }


    public static void addPost(String username, Post post) throws SQLException {
        Connection con = initializeConnection();
        String query = String.format("insert into post (poster_username, description) values('%s', '%s')", username, post.getDescription());
        Statement statement = con.createStatement();
        statement.executeUpdate(query);
    }

    public static boolean likeExists(Like like) throws SQLException {
        Connection con = initializeConnection();
        String query = String.format("select exists (select * from `like` where username = '%s' and post_id = '%s') as like_", like.getUsername(), like.getPostId());
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);

        int count = 0;
        while(rs.next()){
            count = rs.getInt("like_");
        }
        return count == 1;
    }

    public static void addLike(Like like) throws SQLException {
        Connection con = initializeConnection();
        Statement statement = con.createStatement();
        String query = String.format("insert into `like` (username, post_id) values('%s', '%s')", like.getUsername(), like.getPostId());
        String query2 = String.format("update post set likes=likes+1 where id=%s", like.getPostId());
        statement.addBatch(query);
        statement.addBatch(query2);
        statement.executeBatch();
    }

    public static void addComment(Comment comment) throws SQLException {
        Connection con = initializeConnection();
        String query = String.format("insert into comment (comment, username, post_id) values('%s', '%s', '%s')", comment.getComment(), comment.getUsername(), comment.getPostId());
        Statement statement = con.createStatement();
        statement.executeUpdate(query);
    }
    public static void updateComment(int id, String comment) throws SQLException {
        Connection con = initializeConnection();
        String query = String.format("update comment set comment = '%s' where id = %s", comment, id);
        Statement statement = con.createStatement();
        statement.executeUpdate(query);
    }
    public static void deleteComment(int commentId) throws SQLException {
        Connection con = initializeConnection();
        String query = String.format("delete from comment where id=%s", commentId);
        Statement statement = con.createStatement();
        statement.executeUpdate(query);
    }
    public static boolean postIdExists(int postId) throws SQLException {
        Connection con = initializeConnection();
        String query = String.format("select exists (select * from post where id = '%s') as post_count", postId);
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);
        int count = 0;
        while(rs.next()){
            count = rs.getInt("post_count");
        }
        return count > 0;
    }

    public static boolean accountCredentialsExist(String username, String password) throws SQLException{
        Connection con = initializeConnection();
        String query = String.format("select exists (select * from account where username = '%s' and password = '%s') as account", username, password);
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);

        int count = 0;
        while(rs.next()){
            count = rs.getInt("account");
        }

        return count == 1;
    }

    public static boolean usernameExists(String username) throws SQLException {
        Connection con = initializeConnection();
        String query = String.format("select exists (select * from account where username = '%s') as username_count", username);
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);
        int count = 0;
        while(rs.next()){
            count = rs.getInt("username_count");
        }
        return count > 0;
    }
    public static boolean accountIdExists(int id) throws SQLException {
        Connection con = initializeConnection();
        String query = String.format("select exists (select * from account where id = %s) as id_count", id);
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);
        int count = 0;
        while(rs.next()){
            count = rs.getInt("id_count");
        }
        return count > 0;
    }
    public static ArrayList<Post> fetchPosts(int accountId) throws SQLException {
        Connection con = initializeConnection();
        String query = String.format("select * from post inner join account on post.poster_username=account.username where account.id=%s", accountId);
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);

        ArrayList<Post> posts = new ArrayList<>();
        while(rs.next()){
            int id = rs.getInt("id");
            String description = rs.getString("description");
            String poster_username = rs.getString("poster_username");
            int likes = rs.getInt("likes");
            posts.add(new Post(id, description, poster_username, likes));
        }

        return posts;
    }

    public static ArrayList<Comment> fetchComments(int postId) throws SQLException {
        Connection con = initializeConnection();
        String query = String.format("select * from comment where post_id = %s", postId);
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);

        ArrayList<Comment> comments = new ArrayList<>();
        while(rs.next()){
            int id = rs.getInt("id");
            String comment = rs.getString("comment");
            String username = rs.getString("username");
            int post_id = rs.getInt("post_id");
            comments.add(new Comment(id, comment, username, post_id));
        }

        return comments;
    }

    public static boolean commentIdExists(int id) throws SQLException {
        Connection con = initializeConnection();
        String query = String.format("select exists (select * from comment where id = %s) as id_count", id);
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);
        int count = 0;
        while(rs.next()){
            count = rs.getInt("id_count");
        }
        return count > 0;
    }

    public static boolean commentIdBelongsToUser(String username, int commentId) throws SQLException {
        Connection con = initializeConnection();
        String query = String.format("select exists (select * from comment where username = '%s' and id = %s) as comment_count", username, commentId);
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);
        int count = 0;
        while(rs.next()){
            count = rs.getInt("comment_count");
        }
        return count > 0;
    }


}
