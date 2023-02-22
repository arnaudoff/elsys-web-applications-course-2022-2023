package com.example.instagramapihomework2;

import java.io.*;
import java.sql.SQLException;
import java.util.stream.Collectors;

import Models.Account;
import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "registerServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    private String message;
    private Gson gson;

    public void init() {
        message = "register";
        gson = new Gson();
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        https://stackoverflow.com/a/42384173/18301773
//        https://www.baeldung.com/java-jdbc-convert-resultset-to-json

        String requestData = request.getReader().lines().collect(Collectors.joining());
        Account newAcc = gson.fromJson(requestData, Account.class);
        try {
            if(!DAO.usernameExists(newAcc.getUsername())){
                DAO.addAccount(newAcc);
                response.setStatus(HttpServletResponse.SC_CREATED);
            }else{
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public void destroy() {

    }
}
