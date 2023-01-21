package com.example.demo;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet(name = "Register", value = "/register")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


        Gson gson = new Gson();
        User user = gson.fromJson(jb.toString(),User.class);

        try
        {
            // create a mysql database connection
            String myUrl = "jdbc:mysql://localhost:3306";
            String myDriver = "com.mysql.cj.jdbc.Driver";
            Class.forName(myDriver);

            Connection conn = DriverManager.getConnection(myUrl, "root", "12345678");

            // the mysql insert statement
            String query = "INSERT INTO instagram.users (first_name, second_name, username, password) " + "VALUES (?,?,?,?)";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, user.getFirstName());
            preparedStmt.setString (2,user.getLastName());
            preparedStmt.setString (3, user.getUsername());
            preparedStmt.setInt(4, user.getPassword().hashCode());

            preparedStmt.execute();

            conn.close();

            HttpSession session = request.getSession();
            session.setAttribute("username",user.getUsername());
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }


    }
}
