package com.instagramapi;

import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession(false) != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        User user;
        try{
            BufferedReader reader = request.getReader();
            user = new Gson().fromJson(reader, User.class);
        }catch(Exception ignored){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if(!User.getUser(user.getId()).getPassword().equals(user.getPassword())){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute("userId", user.getId());
    }
}
