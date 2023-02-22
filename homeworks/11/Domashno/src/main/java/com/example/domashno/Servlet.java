package com.example.domashno;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/Register")
public class Servlet extends HttpServlet {
    UsersMasive usersMasive = new UsersMasive();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

          if(usersMasive.Add(new Users(Integer.valueOf(request.getParameter("id")),request.getParameter("name"),request.getParameter("email"),request.getParameter("password"))) == true) response.setStatus(200);

          else response.setStatus(400);
         response.getWriter().write(usersMasive.getUsersMap().get(0).getName());

            try {

                // Initialize the database

                String dbDriver = "com.mysql.cj.jdbc.Driver";
                String dbURL = "jdbc:mysql://localhost:3306";
                // Database name to access

                String dbUsername = "root";
                String dbPassword = "123456789a";

                Class.forName(dbDriver);
                Connection con = DriverManager.getConnection(dbURL,
                        dbUsername,
                        dbPassword);

                // Create a SQL query to insert data into demo table
                // demo table consists of two columns, so two '?' is used
                PreparedStatement st = con
                        .prepareStatement("insert into instagram.users(id,name,email,password)" +
                                " values(?,?,?,? );");

                // For the first parameter,
                // get the data using request object
                // sets the data to st pointer
                st.setInt(1, Integer.valueOf(request.getParameter("id")));

                // Same for second parameter
                st.setString(2, request.getParameter("name"));
                st.setString(3, request.getParameter("email"));
                st.setString(4, request.getParameter("password"));


                // Execute the insert command using executeUpdate()
                // to make changes in database
                st.executeUpdate();

                // Close all the connections

                con.close();

                response.setStatus(200);

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            response.getWriter().close();
    }

    public UsersMasive returnUsersMasive(){
        return usersMasive;
    }

}
