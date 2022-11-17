package com.example.domashno_;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    User user = new User();
    private String name;
    private String pass;

    private String id;
    public boolean check(String name, String pass){
        for(int i=0; i < user.users.size();i++){
            if((user.users.get(i).getName().equals(name)) && (user.users.get(i).getPassword().equals(pass))){
                id = user.users.get(i).getId();
                return true;
            }
        }
        return false;
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        name = request.getParameter("name");
        pass = request.getParameter("pass");


        if(check(name, pass) == true){
            HttpSession session = request.getSession();
            session.setAttribute("ID", id);
            response.getWriter().write(id);
            response.setStatus(200);
        }else{
            response.setStatus(400);
        }


    }


}