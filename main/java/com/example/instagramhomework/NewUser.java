package com.example.instagramhomework;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/newuser")
public class NewUser extends HttpServlet {
    private int getUserId(String username, Connection connection) throws SQLException {
        String query =
                "select userid from users where username='"+username+"'";
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery(query);

        resultSet.next();
            return resultSet.getInt(1);
    }


    public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {

        //make an if if they are null
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");


        int userId;
        try {
            Connection connection = DatabaseConnection.initializeDatabase();
            PreparedStatement st = connection
                    .prepareStatement("insert into users(firstname,lastname,username,password) values(?,?,?,?)");

            st.setString(1, firstName);
            st.setString(2, lastName);
            st.setString(3, username);
            st.setString(4, password);

            st.executeUpdate();

            st.close();
            userId = getUserId(username, connection);
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        HttpSession session = request.getSession();
        //should use a query to retrieve the userid
        session.setAttribute("userid", userId);


    }
}
