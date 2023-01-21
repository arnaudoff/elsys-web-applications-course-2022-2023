package com.example.demo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class User extends HelloServlet{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        super.doGet(request, response);
        String FirstName = request.getParameter("FirstName");
        String SecondName = request.getParameter("SecondName");
        String Password = request.getParameter("Password");

        //Account user = new Account(FirstName, SecondName, Password);
        PrintWriter out = response.getWriter();
        //out.println("Welcome" + user.getUsername());
        out.close();
    }
}
