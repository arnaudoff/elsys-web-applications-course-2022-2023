package com.example.instagramap;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "postsServlet", value = "/posts-servlet")
public class PostsServlet extends HttpServlet {
    private int user_id;
    private String image;
    private String description;
    private int post_id;
    private int comment_id;

    public void init(){
        image = "";
        description = "";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        user_id = Integer.parseInt(request.getParameter("accountId"));
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "toor");
            String sql = "select * from post where user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, String.valueOf(user_id));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String pathInfo = request.getPathInfo();
        String[] splittedPath = pathInfo.split("/");
        post_id = Integer.parseInt(splittedPath[1]);

        if (splittedPath[2] == "comments") {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "toor");
                String sql = "select * from comments where post_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, String.valueOf(post_id));
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    System.out.println(resultSet.getString(2));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        user_id = LoginServlet.getUserId();
        image = request.getParameter("image");
        description = request.getParameter("description");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "toor");
            String sql = "insert into post(user_id, image, description)" + " values (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, String.valueOf(user_id));
            statement.setString(2, image);
            statement.setString(3, description);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String pathInfo = request.getPathInfo();
        String[] splittedPath = pathInfo.split("/");
        post_id = Integer.parseInt(splittedPath[1]);

        if (splittedPath.length > 2) {
            if (splittedPath[2] == "comments") {
                if (splittedPath.length > 3) {
                    comment_id = Integer.parseInt(splittedPath[3]);
                }
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "toor");
                    String sql = "insert into comment(post_id, description)" + " values (?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, String.valueOf(post_id));
                    statement.setString(2, description);
                    statement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (splittedPath[2] == "likes") {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "toor");
                    String sql = "insert into likes(post_id, user_id)" + " values (?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, String.valueOf(post_id));
                    statement.setString(2, String.valueOf(user_id));
                    statement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/html");

        String pathInfo = request.getPathInfo();
        String[] splittedPath = pathInfo.split("/");
        post_id = Integer.parseInt(splittedPath[1]);

        description = request.getParameter("description");

        if (splittedPath[2] == "comments") {
            comment_id = Integer.parseInt(splittedPath[3]);
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "toor");
                String sql = "update comments set description = ? where comments_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, description);
                statement.setString(2, String.valueOf(comment_id));
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/html");

        String pathInfo = request.getPathInfo();
        String[] splittedPath = pathInfo.split("/");
        post_id = Integer.parseInt(splittedPath[1]);

        if (splittedPath[2] == "comments") {
            comment_id = Integer.parseInt(splittedPath[3]);
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "toor");
                String sql = "delete from comments where comments_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, String.valueOf(comment_id));
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
