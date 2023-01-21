package com.example.domashno;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet(name = "Delete", value = "/Delete/*")
public class Delete extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session!=null) {
            String path;
            path = request.getPathInfo();
            String parts[] = path.split("/");
            int ID = Integer.valueOf( parts[1]);
            int comment_id = Integer.valueOf( parts[3]);
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
                        .prepareStatement("DELETE from instagram.coments where  id='"+ID+"' and  coment_id='"+comment_id+"'");





                st.executeUpdate();


                con.close();

                response.setStatus(200);

            }
            catch (Exception e) {
                e.printStackTrace();
            }


        }
        else{
            response.sendRedirect("/Login");
        }

    }
}
