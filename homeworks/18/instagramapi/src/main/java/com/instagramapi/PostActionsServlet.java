package com.instagramapi;


import com.google.gson.Gson;
import com.google.gson.JsonObject;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "PostActionsServlet", value = "/posts/*")
public class PostActionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession(false) == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String path = request.getPathInfo();
        String[] path_split = path.split("/");
        if(path_split.length < 3 || !path_split[2].equals("comments")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Post post;
        try{
            post = Post.getPost(Long.valueOf(path_split[1]));
        }catch(Exception ignored){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        HashMap<Long, String> comments = post.getComments();
        String json = new Gson().toJson(comments);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession(false) == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String path = request.getPathInfo();
        String[] path_split = path.split("/");
        if(path_split.length < 3 || !path_split[2].equals("comments")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Post post;
        try{
            post = Post.getPost(Long.valueOf(path_split[1]));
        }catch(Exception ignored){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader br = request.getReader();
        String str;
        while( (str = br.readLine()) != null ){
            sb.append(str);
        }
        JsonObject jObj = new Gson().fromJson(sb.toString(), JsonObject.class);
        try{
            post.editComment(Long.valueOf(path_split[3]), jObj.get("new_comment").toString().replace("\"", ""));
        }catch(Exception ignored) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession(false) == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String path = request.getPathInfo();
        String[] path_split = path.split("/");
        if(path_split.length < 3 || !path_split[2].equals("comments")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Post post;
        try{
            post = Post.getPost(Long.valueOf(path_split[1]));
        }catch(Exception ignored){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        try{
            post.deleteComment(Long.valueOf(path_split[3]));
        }catch(Exception ignored){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession(false) == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String path = request.getPathInfo();
        String[] path_split = path.split("/");

        if(path_split[2].equals("likes")){
            try{
                Post.getPost(Long.valueOf(path_split[1])).like((Long) request.getSession().getAttribute("userId"));
            }catch(Exception ignored){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        else if(path_split[2].equals("comments")){
            Post post;
            try{
                post = Post.getPost(Long.valueOf(path_split[1]));
            }catch(Exception ignored){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            StringBuilder sb = new StringBuilder();
            BufferedReader br = request.getReader();
            String str;
            while( (str = br.readLine()) != null ){
                sb.append(str);
            }
            JsonObject jObj = new Gson().fromJson(sb.toString(), JsonObject.class);
            post.addComment(jObj.get("comment").toString().replace("\"", ""));
        }
    }
}
