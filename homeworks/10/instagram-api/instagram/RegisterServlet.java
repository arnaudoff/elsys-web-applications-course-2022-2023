package com.example.instagram;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    private String username;
    private String password;
    private String first_name;
    private String last_name;

    public void init() {
        username = "";
        password = "";
        first_name = "";
        last_name = "";
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        username = req.getParameter("username");
        first_name = req.getParameter("first_name");
        last_name = req.getParameter("last_name");
        password = req.getParameter("password");
        password = String.valueOf(password.hashCode());

        try{

            String sql = "insert into login(last_name, first_name, username, password)" + " values (?, ?, ?, ?)";
            Connection connection = getDbConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, last_name);
            statement.setString(2, first_name);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        resp.sendError(200, "Successfully registered");
    }

    public static Connection getDbConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/servletsdb", "root", "1Viki_2007!");
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}