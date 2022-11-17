package com.instagramapi;

import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "PostServlet", value = "/posts")
public class PostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession(false) == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        long userId;
        try{
            userId = Long.parseLong(request.getParameter("accountId"));
        }catch(Exception ignored){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        ArrayList<Post> posts = Post.getPostsByUser(userId);
        String json = new Gson().toJson(posts);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession(false) == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        Post post;
        try{
            BufferedReader reader = request.getReader();
            post = new Gson().fromJson(reader, Post.class);
        }catch(Exception ignored){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        post.generateId();
        Post.addPost(post, (Long) request.getSession().getAttribute("userId"));
    }
}
