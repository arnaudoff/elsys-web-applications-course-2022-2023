package com.example.instagram_api;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "ServletPosts", urlPatterns = "/posts/*")
public class ServletPosts extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Account> accounts = (ArrayList<Account>) getServletConfig().getServletContext().getAttribute("accounts");
        String path = request.getPathInfo();
        PrintWriter writer = response.getWriter();

        if (path != null) {
            Matcher pattern = Pattern.compile("(?<postId>0|[1-9]\\d*)/(?<action>comments)").matcher(path);
            int id = Integer.parseInt(pattern.group("accountId"));
            System.out.println(id);
            if (pattern.group("action").equals(null)) {
                for (Account account : accounts) {
                    if (account.getId() == id) {
                        writer.println(account.getPosts().toString());
                    }
                }
            }
            else{
                for (Account account : accounts) {
                    if (account.getId() == id) {
                        for (Post post : account.getPosts()){
                            writer.println(post.getComments().toString());
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Account> accounts = (ArrayList<Account>) getServletConfig().getServletContext().getAttribute("accounts");
        String path = request.getPathInfo();
        Account currentUser = (Account) getServletConfig().getServletContext().getAttribute("currentUser");

        if (currentUser == null) {
            System.out.println("Log in first");
            return;
        }
        if (path == null) {
            String picture = request.getParameter("picture");
            String description = request.getParameter("description");
            Post newPost = new Post(picture, description, currentUser);
            currentUser.addPost(newPost);
            System.out.println("New post was created");
        }
        else {
            Matcher pattern = Pattern.compile("(?<postId>0|[1-9]\\d*)/(?<action>comments|likes)").matcher(path);
            int id = Integer.parseInt(pattern.group("postId"));
            String action = pattern.group("action");
            System.out.println(id);
            Post post = null;
            try {
                post = Post.findPost(id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (currentUser.equals(post.getAuthor())) {
                System.out.println("You cannot comment or like your own posts");
                return;
            }
            if (action.equals("comments")) {
                Comment comment = new Comment(request.getParameter("text"), currentUser);
                post.addComment(comment);
                System.out.println("New comment was added");
            }
            else {
                int likes = post.getLikes();
                post.addLikeFrom(currentUser);
                if (likes == post.getLikes()) {
                    System.out.println("You cannot like this post again");
                    return;
                }
                System.out.println("Liked");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Matcher pattern = Pattern.compile("(?<postId>0|[1-9]\\d*)/comments/(?<commentId>0|[1-9]\\d*)").matcher(req.getPathInfo());
        int postId = Integer.parseInt(pattern.group("postId"));
        int commentId = Integer.parseInt(pattern.group("commentId"));
        Post post = null;
        try {
            post = Post.findPost(postId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            post.deleteComment(commentId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
