package com.example.instagram;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<form action=\"login\" method=\"post\">");
        out.println("Username: <input type=\"text\" name=\"username\"><br>");
        out.println("Password: <input type=\"password\" name=\"password\"><br>");
        out.println("<input type=\"submit\" value=\"Login\"></form>");
        out.println("</body></html>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        int password = request.getParameter("password").hashCode();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "password");
            PreparedStatement preparedStatement = connection.prepareStatement("select * from accounts where username=? and passwordHash=?");
            preparedStatement.setString(1,username);
            preparedStatement.setInt(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean status = resultSet.next();
            if (status) {
                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("username", username);
                request.getRequestDispatcher("/")
                        .forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("Something went wrong...");
        }
    }
}
