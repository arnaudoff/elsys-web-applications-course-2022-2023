package com.example.instagramhw.endpoints;

import com.example.instagramhw.models.ReadRequest;
import com.example.instagramhw.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = ReadRequest.readRequest(request);
        JsonObject convertedObject = new Gson().fromJson(data, JsonObject.class);
        String username = convertedObject.get("username").getAsString();
        String password = convertedObject.get("password").getAsString();

        for (User user : User.getUsers()){
            if(user.getUsername().equals(username)){
                if(user.getPassword().equals(password)){
                    HttpSession session = request.getSession();
                    if (session != null) {
                        session.setAttribute("user", user);
                        response.getWriter().println("You successfully login.");
                        return;
                    } else {
                        response.setStatus(401);
                        return;
                    }
                } else {
                    response.setStatus(401);
                    response.getWriter().println("Wrong password.");
                    return;
                }
            }
        }
        response.getWriter().println("Can't find user with this username.");
    }
}
