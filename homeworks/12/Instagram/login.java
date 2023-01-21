package org.elsys.hwservlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "login", value = "/login")
public class login extends HttpServlet {
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
        String username = convertedObject.get("username").getAsString();
        String password = convertedObject.get("password").getAsString();

        for (int i = 0; i < User.getUserVector().size(); i++) {
            if (User.getUserVector().get(i).getUsername().equals(username)) {
                if (User.getUserVector().get(i).getPassword().equals(password)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("User", User.getUserVector().get(i));
                    response.setStatus(200);
                    return;
                }
            }
        }
        response.setStatus(400);
    }
}
