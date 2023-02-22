package com.example.instagramhw.endpoints;

import com.example.instagramhw.models.ReadRequest;
import com.example.instagramhw.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = ReadRequest.readRequest(request);
        JsonObject convertedObject = new Gson().fromJson(data, JsonObject.class);
        String firstName = convertedObject.get("firstName").getAsString();
        String lastName = convertedObject.get("lastName").getAsString();
        String username = convertedObject.get("username").getAsString();
        String password = convertedObject.get("password").getAsString();

        User user = new User(firstName, lastName, username, password);
        user.addUser(user);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        response.getWriter().println("You successfully register an account with:\nFirstname: " + firstName + "\n"
                + "Lastname: " + lastName + "\n" + "Username: " + username + "\n" + "Password: " + password);
    }
}
