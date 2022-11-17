package com.example.instagramapi;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, User> users = (HashMap<String, User>) getServletConfig().getServletContext().getAttribute("database");
        String firstname = req.getParameter("firstName");
        String lastname = req.getParameter("lastName");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if(users.containsKey(username)){
            resp.setStatus(409);
            resp.getWriter().println("Username is taken!");
            return;
        }
        User newUser = new User(firstname, lastname, username, password);
        users.put(username, newUser);
        resp.setStatus(201);
        resp.getWriter().println(newUser);
    }
}
