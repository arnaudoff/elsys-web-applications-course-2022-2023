package com.example.instagram;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    private static boolean loggedIn;
    private String username;
    private String password;
    protected static int user_id;


    public void init() {
        username = "";
        password = "";
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        username = req.getParameter("username");
        password = req.getParameter("password");
        password = String.valueOf(password.hashCode());

        try{
            Connection connection = RegisterServlet.getDbConnection();
            String sql = "select * from login where username = ? and password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs=statement.executeQuery();

            loggedIn=rs.next();
            user_id = rs.getInt("id");

        }catch (Exception e){
            e.printStackTrace();
        }

        if(loggedIn){
            resp.sendError(200, "Successfully logged in");
        }else {
            resp.sendError(403, "Invalid username or password");
        }
    }

    public boolean isLoggedIn(){
        return loggedIn;
    }
}
