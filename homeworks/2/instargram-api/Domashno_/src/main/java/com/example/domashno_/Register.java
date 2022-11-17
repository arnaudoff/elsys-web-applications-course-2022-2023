package com.example.domashno_;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "Register", value = "/Register")
public class Register extends HttpServlet {


    public Register() {
    }

    private String ID;
    private int counter;
    User user = new User();
    private String name;
    private String lastname;
    private String email;
    private String pass;
    public boolean addUsers( String email) {
        for (int i = 0; i < user.users.size(); i++) {
            if (user.users.get(i).getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

         name = request.getParameter("name");
         lastname = request.getParameter("lastname");
         email = request.getParameter("email");
         pass = request.getParameter("pass");
         if(addUsers(email) == true && lastname != null && email !=null && pass !=null && lastname !=null){
             counter++;
         User user = new User(counter, name, lastname, email, pass);
         ID = user.getId();
         user.users.add(user);
             HttpSession session = request.getSession();
             session.setAttribute("ID", ID);
             response.getWriter().write(name);
             response.getWriter().write(lastname);
             response.setStatus(200);
         }else{
             response.getWriter().write("Cant create the same user/Cant have blank fields.");
             response.setStatus(400);
         }




    }


}