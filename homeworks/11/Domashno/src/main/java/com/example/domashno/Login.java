package com.example.domashno;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

@WebServlet("/Login")
public class Login extends HttpServlet {

    private String Name;
    private String Password;
    UsersMasive usersMasive = new UsersMasive();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Name = request.getParameter("name");
        Password = request.getParameter("password");
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

            PreparedStatement st = con
                    .prepareStatement("select * from instagram.users where  name='"+Name+"' and  password='"+Password+"'");
            ResultSet a = st.executeQuery();
            a.next();
            response.getWriter().write(a.getString(1));
            response.getWriter().write(a.getString(2));
            response.getWriter().write(a.getString(4));



            if ((Name.equals(a.getString(2)))&&(Password.equals(a.getString(4)))) {

                HttpSession session=request.getSession(true);
                session.setAttribute("id",String.valueOf( a.getString(1)));

                response.getWriter().write(a.getString(2));
            }


            else{

                response.setStatus(400);
            }

            // Close all the connections

            con.close();

            response.setStatus(200);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        response.getWriter().close();



    }


}