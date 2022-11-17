package com.example.instagram_api;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "ServletRegister", urlPatterns = "/register", loadOnStartup=1)
public class ServletRegister extends HttpServlet {
    @Override
    public void init() throws ServletException {
        getServletConfig().getServletContext().setAttribute("accounts", new ArrayList<Account>());
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Account> accounts = (ArrayList<Account>) getServletConfig().getServletContext().getAttribute("accounts");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!accounts.isEmpty()) {
            for (Account account : accounts) {
                if (account.getUsername() == username && account.getPassword() == password) {
                    System.out.println("Existing account");
                    System.out.println("Register again with different username or login");
                    return;
                }
            }
        }
        Account account = new Account(firstName, lastName, username, password);
        System.out.println("New account is created");
        accounts.add(account);
    }
}
