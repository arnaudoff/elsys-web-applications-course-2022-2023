package com.example.instagramapi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "LogInServlet", urlPatterns = "/login")
public class LogInServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, User> users = (HashMap<String, User>) getServletConfig().getServletContext().getAttribute("database");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if(!users.containsKey(username)){
            resp.setStatus(404);
            resp.getWriter().println("Username does not exist!");
            return;
        }

        if(!users.get(username).getPassword().equals(password)){
            resp.setStatus(409);
            resp.getWriter().println("Wrong password!");
            return;
        }

        getServletConfig().getServletContext().setAttribute("profile", username);
    }
}
