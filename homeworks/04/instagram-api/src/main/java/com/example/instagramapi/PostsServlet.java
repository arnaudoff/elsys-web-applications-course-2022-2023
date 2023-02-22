package com.example.instagramapi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;


@WebServlet(name = "PostsServlet", urlPatterns = "/posts/*", loadOnStartup = 1)
public class PostsServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        getServletConfig().getServletContext().setAttribute("database", new HashMap<String, User>());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        HashMap<String, User> users = (HashMap<String, User>) getServletConfig().getServletContext().getAttribute("database");
        String currentUser = (String) getServletConfig().getServletContext().getAttribute("profile");

        if(currentUser == null){
            resp.setStatus(401);
            resp.getWriter().println("Please log in before posting/commenting/liking!");
            return;
        }

        if(pathInfo == null){
            Post newPost = new Post(req.getParameter("image"), req.getParameter("description"), currentUser);
            users.get(currentUser).addPost(newPost);
            resp.setStatus(201);
            resp.getWriter().println(newPost);
            return;
        }

        String[] splitPath = pathInfo.split("/");
        if(splitPath.length != 3){
            resp.setStatus(404);
            resp.getWriter().println("Incorrect path!");
            return;
        }

        int postId = isNumeric(splitPath[1]);

        if(postId == -1){
            resp.setStatus(409);
            resp.getWriter().println("Id can only be a number!");
            return;
        }

        Post post;
        try {
            post = Post.getPost(postId);
        } catch (IllegalArgumentException e){
            resp.setStatus(404);
            resp.getWriter().println("Post not found!");
            return;
        }

        if(currentUser.equals(post.getAuthor())){
            resp.setStatus(409);
            resp.getWriter().println("It is impossible to comment or like your won posts!");
            return;
        }

        if(splitPath[2].equals("comments")){
            Comment newComment = new Comment(req.getParameter("text") ,currentUser);
            post.addComment(newComment);
            resp.setStatus(201);
            resp.getWriter().println(newComment);
        } else if (splitPath[2].equals("likes")) {
            int likes = post.getLikes();
            post.like(currentUser);
            if(likes == post.getLikes()){
                resp.setStatus(409);
                resp.getWriter().println("You can't like a post again!");
            }
        } else {
            resp.setStatus(404);
            resp.getWriter().println("Invalid path!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        HashMap<String, User> users = (HashMap<String, User>) getServletConfig().getServletContext().getAttribute("database");

        if(pathInfo == null){
            String username = req.getParameter("accountId");
            if(!users.containsKey(username)){
                resp.setStatus(404);
                resp.getWriter().println("User not found!");
                return;
            }
            if(username.equals(getServletConfig().getServletContext().getAttribute("profile"))){
                resp.setStatus(409);
                resp.getWriter().println("You can't retrieve your posts!");
                return;
            }
            resp.setStatus(202);
            resp.getWriter().println(users.get(username).getPosts());
            return;
        }

        String[] splitPath = pathInfo.split("/");
        if(splitPath.length != 3){
            resp.setStatus(404);
            resp.getWriter().println("Incorrect path!");
            return;
        }

        if(!splitPath[2].equals("comments")){
            resp.setStatus(404);
            resp.getWriter().println("Invalid path!");
            return;
        }

        int postId = isNumeric(splitPath[1]);
        if(postId == -1){
            resp.setStatus(409);
            resp.getWriter().println("Post Id can only be a number!");
            return;
        }

        Post post;
        try {
            post = Post.getPost(postId);
        } catch (IllegalArgumentException e){
            resp.setStatus(404);
            resp.getWriter().println("Post not found!");
            return;
        }

        resp.setStatus(202);
        resp.getWriter().println(post.getComments());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        HashMap<String, User> users = (HashMap<String, User>) getServletConfig().getServletContext().getAttribute("database");
        String currentUser = (String) getServletConfig().getServletContext().getAttribute("profile");

        String[] splitPath = pathInfo.split("/");
        if(splitPath.length != 4){
            resp.setStatus(404);
            resp.getWriter().println("Incorrect path!");
            return;
        }

        if(!splitPath[2].equals("comments")){
            resp.setStatus(404);
            resp.getWriter().println("Invalid path!");
            return;
        }

        if(currentUser == null){
            resp.setStatus(401);
            resp.getWriter().println("Please log in before changing the comment!");
            return;
        }

        int postId = isNumeric(splitPath[1]);
        int commentId = isNumeric(splitPath[3]);

        if(postId == -1){
            resp.setStatus(409);
            resp.getWriter().println("Post Id can only be a number!");
            return;
        }

        if(commentId == -1){
            resp.setStatus(409);
            resp.getWriter().println("Comment Id can only be a number!");
            return;
        }

        Post post;
        try {
            post = Post.getPost(postId);
        } catch (IllegalArgumentException e){
            resp.setStatus(404);
            resp.getWriter().println("Post not found!");
            return;
        }

        Comment comment;
        try {
            comment = post.getComment(commentId);
        } catch (IllegalArgumentException e){
            resp.setStatus(404);
            resp.getWriter().println("Comment not found!");
            return;
        }

        if(!currentUser.equals(comment.getAuthor())){
            resp.setStatus(401);
            resp.getWriter().println("You are not the author to change this comment!");
            return;
        }

        comment.setText(req.getParameter("comm-text"));
        resp.setStatus(202);
        resp.getWriter().println(comment);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        HashMap<String, User> users = (HashMap<String, User>) getServletConfig().getServletContext().getAttribute("database");
        String currentUser = (String) getServletConfig().getServletContext().getAttribute("profile");

        String[] splitPath = pathInfo.split("/");
        if(splitPath.length != 4){
            resp.setStatus(404);
            resp.getWriter().println("Incorrect path!");
            return;
        }

        if(!splitPath[2].equals("comments")){
            resp.setStatus(404);
            resp.getWriter().println("Invalid path!");
            return;
        }

        if(currentUser == null){
            resp.setStatus(401);
            resp.getWriter().println("Please log in before deleting the comment!");
            return;
        }

        int postId = isNumeric(splitPath[1]);
        int commentId = isNumeric(splitPath[3]);

        if(postId == -1){
            resp.setStatus(409);
            resp.getWriter().println("Post Id can only be a number!");
            return;
        }

        if(commentId == -1){
            resp.setStatus(409);
            resp.getWriter().println("Comment Id can only be a number!");
            return;
        }

        Post post;
        try {
            post = Post.getPost(postId);
        } catch (IllegalArgumentException e){
            resp.setStatus(404);
            resp.getWriter().println("Post not found!");
            return;
        }

        Comment comment;
        try {
            comment = post.getComment(commentId);
        } catch (IllegalArgumentException e){
            resp.setStatus(404);
            resp.getWriter().println("Comment not found!");
            return;
        }

        if(!currentUser.equals(comment.getAuthor())){
            resp.setStatus(401);
            resp.getWriter().println("You are not the author to delete this comment!");
            return;
        }

        post.deleteComment(commentId);
        resp.setStatus(202);
    }

    public static int isNumeric(String string) {
        int intValue;

        try {
            intValue = Integer.parseInt(string);
            return intValue;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }
        return -1;
    }
}
