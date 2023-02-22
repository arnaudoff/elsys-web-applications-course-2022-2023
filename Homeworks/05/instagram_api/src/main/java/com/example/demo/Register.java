package com.example.demo;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "register", value = "/register")
public class Register extends HelloServlet{

    private static ArrayList<UserInfo> users;
    private static int counter = 0;
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        super.doGet(request, response);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
      //  request.getRequestDispatcher("/register.jsp").include(request, response);
        out.close();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        UserInfo userInfo = new UserInfo(counter++, req.getParameter("firstName"), req.getParameter("lastName"), req.getParameter("username"), req.getParameter("password").hashCode());
        users.add(userInfo);
    }

    public static ArrayList<UserInfo> getUsers() {
        return users;
    }
}
