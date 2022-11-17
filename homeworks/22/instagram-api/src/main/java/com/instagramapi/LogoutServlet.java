package com.instagramapi;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Utils.initRequest(request, response)){
            return;
        }
        request.getSession().invalidate();
        Utils.setErrorOrSuccessMessage(response, "Successfully logged out.");
    }
}
