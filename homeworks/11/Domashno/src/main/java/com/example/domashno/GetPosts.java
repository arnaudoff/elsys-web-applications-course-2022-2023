package com.example.domashno;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "GetPosts", value = "/GetPosts")
public class GetPosts extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session!=null){



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
                        .prepareStatement("select * from instagram.pictures where  id='"+Integer.valueOf(request.getParameter("id"))+"'");

                ResultSet a = st.executeQuery();

                response.getWriter().write(a.getString(1));
                response.getWriter().write(a.getString(2));
                response.getWriter().write(a.getString(3));
                response.getWriter().write(a.getString(4));

;

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
