package com.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import com.google.gson.Gson;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var users = (HashMap) getServletConfig().getServletContext().getAttribute("users");
        var username = req.getParameter("username");
        var body = resp.getWriter();
        if (users.containsKey(username)) {
            resp.setStatus(409);
            body.println("User with this username is already registered!");
            return;
        }
        var user = new User(req.getParameter("firstName"), req.getParameter("lastName"), username, req.getParameter("password"));
        users.put(username, user);
        resp.setStatus(201);
        body.println(new Gson().toJson(user));
    }
}
