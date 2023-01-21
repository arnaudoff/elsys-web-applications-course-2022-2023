package com.example.domashno;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet(name = "Coments", value = "/Coments/*")
public class Coments extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session!=null){
            String path;
            path = request.getPathInfo();
            String parts[] = path.split("/");
            String ID = parts[1];
            String comment = parts[2];


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
                        .prepareStatement("insert into instagram.coments(id,coment)" +
                                " values(?,?);");


                st.setInt(1, Integer.valueOf(ID));
                st.setString(2, comment);



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
