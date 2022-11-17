package com.example.instagram;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet(name = "register", value = "/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<form action=\"register\" method=\"post\">");
        out.println("First Name: <input type=\"text\" name=\"firstName\"><br>");
        out.println("Last Name: <input type=\"text\" name=\"lastName\"><br>");
        out.println("Username: <input type=\"text\" name=\"username\"><br>");
        out.println("Password: <input type=\"password\" name=\"password\"><br>");
        out.println("<input type=\"submit\" value=\"Submit\"></form>");
        out.println("</body></html>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String username = request.getParameter("username");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        int password = request.getParameter("password").hashCode();

        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "password");
            PreparedStatement preparedStatement = connection.prepareStatement("insert into accounts(firstName, lastName, username, passwordHash) values(?,?,?,?)");
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.setString(3,username);
            preparedStatement.setInt(4, password);
            preparedStatement.executeUpdate();

            out.println("Welcome, " + username);
        } catch (Exception e) {
            e.printStackTrace();
            out.print("Something went wrong...");
        }

    }
}