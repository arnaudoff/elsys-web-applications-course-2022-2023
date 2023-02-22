package com.example.demo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }


        JsonObject convertedObject = new Gson().fromJson(jb.toString(), JsonObject.class);




        try
        {
            // create a mysql database connection
            String myUrl = "jdbc:mysql://localhost:3306";
            String myDriver = "com.mysql.cj.jdbc.Driver";
            Class.forName(myDriver);

            Connection conn = DriverManager.getConnection(myUrl, "root", "12345678");

            Statement st = conn.createStatement();

            // the mysql insert statement
            String query = String.format(
                "SELECT password FROM instagram.users WHERE username='%s'", convertedObject.get("username").getAsString());

            ResultSet rs = st.executeQuery(query);
            rs.next();
            int databasePass = rs.getInt("password");


            if(databasePass == convertedObject.get("password").getAsString().hashCode()){
                response.setStatus(200);
                HttpSession session = request.getSession();
                session.setAttribute("username",convertedObject.get("username").getAsString());
            }else{
                response.setStatus(401);
            }
            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

    }
}
