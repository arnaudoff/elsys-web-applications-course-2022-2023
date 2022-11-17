package com.example.instagram;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "CommentServlet", value = "/posts/*")
public class CommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String pathInfo = request.getPathInfo(); // /{id}/comments
        String[] pathParts = pathInfo.split("/");

        String method;
        if(pathParts.length == 2){
            method = "post";
        }
        else {
            method = "put";
        }
        out.println("<html><body>");
        out.println("<form action=\"posts/*\" method=\""+ method +"\">");
        out.println("comment: <input type=\"text\" name=\"comment\"><br>");
        out.println("<input type=\"submit\" value=\"Submit\"></form>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String pathInfo = request.getPathInfo(); // /{id}/comments
        String[] pathParts = pathInfo.split("/");
        String post_id = pathParts[1]; // {id}
//      String part2 = pathParts[2]; // comments
        String comment = request.getParameter("comment");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "password");
            PreparedStatement preparedStatement = connection.prepareStatement("insert into comments(username, comment, post_id) values(?,?,?)");
            preparedStatement.setString(1,request.getSession().getAttribute("username").toString());
            preparedStatement.setString(2,comment);
            preparedStatement.setString(3,post_id);
            preparedStatement.executeUpdate();

            out.println(comment);
        } catch (Exception e) {
            out.print(e);
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String pathInfo = request.getPathInfo(); // /{id}/comments//{id}
        String[] pathParts = pathInfo.split("/");
        String post_id = pathParts[1]; // {id}
//      String part2 = pathParts[2]; // comments
        String comment_id = pathParts[3]; // {id}

        String comment = request.getParameter("comment");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "password");
            String query = " select * from comments where username=? and post_id=? and id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,request.getSession().getAttribute("username").toString());
            preparedStatement.setString(2,post_id);
            preparedStatement.setString(3,comment_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean status = resultSet.next();
            System.out.println(status);
            if(status){
                String query2 = "update comments set comment=? where  id=?";
                preparedStatement = connection.prepareStatement(query2);
                preparedStatement.setString(1, comment);
                preparedStatement.setInt(2,Integer.valueOf(comment_id));
                preparedStatement.executeUpdate();

            }

            out.println(comment);
        } catch (Exception e) {
            out.print(e);
        }
    }
}
