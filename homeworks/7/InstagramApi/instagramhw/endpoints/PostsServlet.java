package com.example.instagramhw.endpoints;

import com.example.instagramhw.models.Comment;
import com.example.instagramhw.models.Post;
import com.example.instagramhw.models.ReadRequest;
import com.example.instagramhw.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "PostsServlet", value = "/posts/*")
public class PostsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user;
        if (session != null) {
            // a session exists
            user = (User) session.getAttribute("user");
        } else {
            resp.setStatus(401);
            return;
        }

        String path = req.getPathInfo();
        String[] path_split = path.split("/");

        if (path_split[2].equals("comments")) {
            int postId = Integer.parseInt(path_split[1]);
            for (Post post : Post.getPosts()) {
                if(post.getId() == postId){
                    resp.getWriter().println("Comments: \n");
                    for(Comment comment : post.getComments()){
                        resp.getWriter().println(comment.getText());
                    }
                    return;
                }
            }
        }
        resp.setStatus(400);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user;
        if (session != null) {
            // a session exists
            user = (User) session.getAttribute("user");
        } else {
            response.setStatus(401);
            return;
        }

        String path = request.getPathInfo();
        String[] path_split = path.split("/");

        if (path_split[2].equals("comments")) {
            String data = ReadRequest.readRequest(request);
            JsonObject convertedObject = new Gson().fromJson(data, JsonObject.class);
            String comment = convertedObject.get("comment").getAsString();

            int postId = Integer.parseInt(path_split[1]);
            for (Post post : Post.getPosts()){
                if(post.getId() == postId){
                    Comment newComment = new Comment(post.getId(), comment);
                    newComment.setUserId(user.getAccountId());
                    post.addComment(newComment);
                    response.getWriter().println("Successfully add comment with text: " + newComment.getText());
                    return;
                }
            }
        }else if(path_split[2].equals("likes")){
            int postId = Integer.parseInt(path_split[1]);
            for (Post post : Post.getPosts()){
                if(post.getId() == postId){
                    post.Like(user);
                    response.getWriter().println("Successfully like post with id: " + post.getId());
                    return;
                }
            }
        }
        response.setStatus(400);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user;
        if (session != null) {
            // a session exists
            user = (User) session.getAttribute("user");
        } else {
            resp.setStatus(401);
            return;
        }

        String path = req.getPathInfo();
        String[] path_split = path.split("/");

        if (path_split[2].equals("comments") && path_split.length == 4) {
            String data = ReadRequest.readRequest(req);
            JsonObject convertedObject = new Gson().fromJson(data, JsonObject.class);
            String newComment = convertedObject.get("comment").getAsString();

            int postId = Integer.parseInt(path_split[1]);
            int commentId = Integer.parseInt(path_split[3]);

            for(Post post : Post.getPosts()){
                if(post.getId() == postId){
                    for(Comment comment : post.getComments()){
                        if(comment.getId() == commentId){
                            if(user.getAccountId() == comment.getUserId()) {
                                String fromText = comment.getText();
                                comment.setText(newComment);
                                resp.getWriter().println("You successfully change your comment from: " + "'" + fromText + "'" + " to: " + "'" + comment.getText() + "'");
                                return;
                            }
                        }
                    }
                }
            }
        }
        resp.setStatus(400);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user;
        if (session != null) {
            // a session exists
            user = (User) session.getAttribute("user");
        } else {
            resp.setStatus(401);
            return;
        }

        String path = req.getPathInfo();
        String[] path_split = path.split("/");
        if (path_split[2].equals("comments") && path_split.length == 4){
            int postId = Integer.parseInt(path_split[1]);
            int commentId = Integer.parseInt(path_split[3]);

            for (Post post : Post.getPosts()) {
                if(post.getId() == postId){
                    for(Comment comment : post.getComments()){
                        if(comment.getId() == commentId){
                            if(user.getAccountId() == comment.getUserId()){
                                String deletedComment = comment.getText();
                                post.removeComment(comment);
                                resp.getWriter().println("You successfully delete your comment: " + "'" + deletedComment + "' " + "on post with id: " + post.getId());
                                return;
                            }else {
                                resp.setStatus(401);
                            }
                        }
                    }
                }
            }
        }
        resp.setStatus(400);
    }
}
