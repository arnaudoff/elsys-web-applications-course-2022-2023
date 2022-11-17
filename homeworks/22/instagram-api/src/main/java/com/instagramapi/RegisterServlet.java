package com.instagramapi;
import com.google.gson.JsonObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession(false) != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Utils.setErrorOrSuccessMessage(response, "Already logged in.");
            return;
        }

        String firstName;
        String lastName;
        String username;
        String password;
        UUID id;
        try{
            JsonObject obj = Utils.readJson(request);
            username = obj.get("username").toString().replace("\"", "");
            password = obj.get("password").toString().replace("\"", "");
            firstName = obj.get("firstName").toString().replace("\"", "");
            lastName = obj.get("lastName").toString().replace("\"", "");
            id = UUID.nameUUIDFromBytes(username.getBytes());
        }catch(Exception ignored){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Incorrect field passed.");
            return;
        }
        if(username.length() < 8 || username.length() > 16){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Username must be between 8 and 16 characters.");
            return;
        }
        try {
            password = Utils.hash(password);
        } catch (Exception ignored) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Utils.setErrorOrSuccessMessage(response, "Error while hashing password.");
            return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/instagram-api", Utils.database_user, Utils.database_pass);
            String query = String.format(
                    "INSERT INTO users (id, firstName, lastName, username, password) " +
                    "VALUES('%s', '%s', '%s', '%s', '%s')",
                    id, firstName, lastName, username, password
                    );
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            st.close();
            conn.close();
            HttpSession session = request.getSession();
            session.setAttribute("accountId", id);
            Utils.setErrorOrSuccessMessage(response, String.format("Successfully registered an account with id - %s", id));
        } catch (SQLException | ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Username already taken.");
        }


    }
}
