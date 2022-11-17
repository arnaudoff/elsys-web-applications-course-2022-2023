package com.example.domashno;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "Like", value = "/Like")
public class Like extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session!=null){
            String id = (String) session.getAttribute("id");
            try {

                String dbDriver = "com.mysql.cj.jdbc.Driver";
                String dbURL = "jdbc:mysql://localhost:3306";

                String dbUsername = "root";
                String dbPassword = "123456789a";

                Class.forName(dbDriver);
                Connection con = DriverManager.getConnection(dbURL,
                        dbUsername,
                        dbPassword);

                PreparedStatement st = con
                        .prepareStatement("insert into instagram.likes(person_id,post_id)" +
                                " values(?,? );");



                st.setString(1, id+"@");
                st.setInt(2, Integer.valueOf(request.getParameter("post_id")));

                st.executeUpdate();


                con.close();

                response.setStatus(200);

            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }else{
            response.sendRedirect("/Login");
        }

    }
}
