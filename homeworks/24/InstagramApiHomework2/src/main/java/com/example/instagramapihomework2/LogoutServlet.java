package com.example.instagramapihomework2;

import java.io.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "logoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {


    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        session.invalidate();


    }

    public void destroy() {
    }
}
