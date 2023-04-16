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

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {
    private String username;
    private String password;
    private boolean logged;
    private static int user_id;

    public void init() {
        username = "";
        password = "";
        logged = false;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        username = request.getParameter("username");
        password = request.getParameter("password");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "toor");
            String sql = "select * from users where username = ? and password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() != false) {
                logged = true;
                user_id = Integer.parseInt(resultSet.getString(1));
            } else {
                response.sendError(404);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }

    public static int getUserId() {
        return user_id;
    }
}

