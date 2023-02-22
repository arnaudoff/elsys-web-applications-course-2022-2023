package org.elsys.hwservlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "register", value = "/register")
public class register extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } finally {
            reader.close();
        }

        JsonObject convertedObject = new Gson().fromJson(String.valueOf(sb), JsonObject.class);
        String firstname = convertedObject.get("firstName").getAsString();
        String lastname = convertedObject.get("lastName").getAsString();
        String username = convertedObject.get("username").getAsString();
        String password = convertedObject.get("password").getAsString();

        User newUser = new User(firstname, lastname, username, password);
        User.addUser(newUser);

        HttpSession session = request.getSession();
        session.setAttribute("User", newUser);
    }
}
