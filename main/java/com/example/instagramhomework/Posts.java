package com.example.instagramhomework;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/posts/*")
public class Posts extends HttpServlet {

    public void doPut(){

    }

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String text = request.getParameter("commentUser");
        HttpSession session = request.getSession();
        try {
            Connection connection = DatabaseConnection.initializeDatabase();
            PreparedStatement st = connection
                    .prepareStatement("insert into comments(textcomment, postid) values(?,?)");

            st.setString(1,text);
            st.setInt(2, (Integer) session.getAttribute("userid"));

            st.executeUpdate();

            st.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        try {
            parseURI(request,response);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void parseURI(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException, ClassNotFoundException {
        response.setContentType("text/html");
        String URI = request.getRequestURI();
        if (URI.trim().equals("/InstagramHomework_war_exploded/posts")){
            request.getRequestDispatcher("/posts.jsp").include(request, response);

        }

       if (request.getQueryString() != null){
           //parse it
           //get user name
           //get number
//            fetchAllComments(){
//
//            }
       }

        String URI2 = URI.replace("/InstagramHomework_war_exploded/posts/", "");
        String[] commands = URI2.split("/");
        Statement st;
         if (commands.length == 2 && commands[1].equals("comments")) {
            request.getRequestDispatcher("/comments.jsp").include(request, response);

        }



    }
}
