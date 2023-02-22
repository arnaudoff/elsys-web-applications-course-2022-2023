package com.example.demo;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
    private String username;
    private int password;

    private static UserInfo logged_user;

    private boolean exists = false;
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        username = req.getParameter("username");
        password = Integer.parseInt(req.getParameter("password"));

        ArrayList<UserInfo> userCheck = Register.getUsers();

        for(int i = 0; i < userCheck.size(); i++){
            if(userCheck.get(i).getUsername().equals(username) && userCheck.get(i).getPassword() == password){
                exists = true;
                logged_user = userCheck.get(i);
                break;
            }
        }

        System.out.println(exists);
        if(!exists){
            try {
                resp.sendError(404, "The user does not exist");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static int getLoggedUserId(){
        return logged_user.getId();
    }
}
