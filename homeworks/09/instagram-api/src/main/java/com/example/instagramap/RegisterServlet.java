package com.example.instagramap;

import java.io.*;
import java.sql.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "registerServlet", value = "/register-servlet")
public class RegisterServlet extends HttpServlet {
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public void init() {
        firstName = "";
        lastName = "";
        username = "";
        password = "";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

//        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "toor");
//            Statement statement = connection.createStatement();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        firstName = request.getParameter("firstName");
        lastName = request.getParameter("lastName");
        username = request.getParameter("username");
        password = request.getParameter("password");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "toor");
            String sql = "insert into users(firstName, lasstName, username, password)" + " values (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }


}