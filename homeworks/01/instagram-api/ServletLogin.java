package com.example.instagram_api;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ServletLogin", urlPatterns = "/login")
public class ServletLogin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Account> accounts = (ArrayList<Account>) getServletConfig().getServletContext().getAttribute("accounts");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean flag = false;
        for (Account account : accounts){
            if (account.getUsername().equals(username) && account.getPassword().equals(password)){
                getServletConfig().getServletContext().setAttribute("currentUser", account);
                System.out.println("Successful login");
                flag = true;
            }
        }
        if (!flag){
            System.out.println("Check your username or password");
            return;
        }
    }
}
