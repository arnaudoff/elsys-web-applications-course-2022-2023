package com.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var users = (HashMap<String, User>) getServletConfig().getServletContext().getAttribute("users");
        var username = req.getParameter("username");
        var password = req.getParameter("password");
        if (!users.containsKey(username)) {
            resp.setStatus(404);
            resp.getWriter().println("No user with this username was found!");
            return;
        }
        if (!password.equals(users.get(username).getPassword())) {
            resp.setStatus(409);
            resp.getWriter().println("Incorrect password!");
            return;
        }
        getServletConfig().getServletContext().setAttribute("currentUser", username);
    }
}
