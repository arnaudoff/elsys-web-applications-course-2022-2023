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

@WebServlet(name = "posts", value = "/posts")
public class posts extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(401);
            return;
        }

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
        Post post = gson.fromJson(jb.toString(),Post.class);





        try
        {
            // create a mysql database connection
            String myUrl = "jdbc:mysql://localhost:3306";
            String myDriver = "com.mysql.cj.jdbc.Driver";
            Class.forName(myDriver);

            Connection conn = DriverManager.getConnection(myUrl, "root", "12345678");

            // the mysql insert statement
            String query = "INSERT INTO instagram.posts (user, pic_url, description,likes) " + "VALUES (?,?,?,?)";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, (String) session.getAttribute("username"));
            preparedStmt.setString (2,post.getPic_url());
            preparedStmt.setString (3, post.getDescription());
            preparedStmt.setInt(4, 0);

            preparedStmt.execute();

            conn.close();

        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

    }
}
