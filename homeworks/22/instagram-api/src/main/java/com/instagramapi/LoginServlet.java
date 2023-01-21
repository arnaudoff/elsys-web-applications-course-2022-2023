package com.instagramapi;

import com.google.gson.JsonObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession(false) != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Utils.setErrorOrSuccessMessage(response, "Already logged in.");
            return;
        }
        String username;
        String password;
        try{
            JsonObject obj = Utils.readJson(request);
            username = obj.get("username").toString().replace("\"", "");
            password = obj.get("password").toString().replace("\"", "");
        }catch(Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Incorrect field passed.");
            return;
        }
        try {
            password = Utils.hash(password);
        } catch (Exception ignored) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Utils.setErrorOrSuccessMessage(response, "Error hashing password.");
            return;
        }
        UUID id = UUID.nameUUIDFromBytes(username.getBytes());
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/instagram-api", Utils.database_user, Utils.database_pass);
            String query = String.format(
                    "SELECT password FROM Users WHERE id='%s'", id
            );
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            String databasePass = rs.getString("password");
            rs.close();
            st.close();
            conn.close();
            if(password.equals(databasePass)){
                HttpSession session = request.getSession();
                session.setAttribute("accountId", id);
            }
            else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                Utils.setErrorOrSuccessMessage(response, "Incorrect username or password.");
                return;
            }
            Utils.setErrorOrSuccessMessage(response, "Successfully logged in!");
        } catch (SQLException | ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Utils.setErrorOrSuccessMessage(response, "Incorrect username or password.");
        }
    }
}
