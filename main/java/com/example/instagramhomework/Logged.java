package com.example.instagramhomework;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@WebServlet("/logged")
public class Logged extends HttpServlet {

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Statement st;
        try{
            Connection connection = DatabaseConnection.initializeDatabase();
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            String query =
                    "select * from users where username='"+username+"' and password='"+password+"'";
            st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(query);
            if (resultSet.next()){
                HttpSession session = request.getSession();
                int userid = getUserId(username,connection);
                session.setAttribute("userid",userid);
            }
            else{
                request.getRequestDispatcher("/login.jsp").include(request, response);
            }

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



    }

    private int getUserId(String username, Connection connection) throws SQLException {
        String query =
                "select userid from users where username='"+username+"'";
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery(query);

        resultSet.next();
        return resultSet.getInt(1);
    }


}
