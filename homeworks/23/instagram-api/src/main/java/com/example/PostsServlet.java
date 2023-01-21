package com.example;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

@WebServlet(name="PostsServlet", urlPatterns="/posts/*", loadOnStartup=1)
public class PostsServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        getServletConfig().getServletContext().setAttribute("users", new HashMap<String, User>());
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null) {
            getUserPosts(req, resp);
            return;
        }
        getPostComments(req, resp);
    }

    private void getUserPosts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var username = req.getParameter("accountId");
        var users = (HashMap<String, User>) getServletConfig().getServletContext().getAttribute("users");
        var body = resp.getWriter();
        if (!users.containsKey(username)) {
            resp.setStatus(404);
            body.println("No user with this username found!");
            return;
        }
        if (username.equals(getServletConfig().getServletContext().getAttribute("currentUser"))) {
            resp.setStatus(409);
            body.println("Can only fetch other users' posts");
            return;
        }
        body.println(new Gson().toJson(users.get(username).getPosts()));
    }

    public void getPostComments(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var pattern = Pattern.compile("(?<postId>0|[1-9]\\d*)/comments").matcher(req.getPathInfo());
        var body = resp.getWriter();
        if (!pattern.find()) {
            resp.setStatus(409);
            body.println("Id should be a number");
            return;
        }
        var id = Integer.parseInt(pattern.group("postId"));
        resp.setStatus(200);
        body.println(new Gson().toJson(Post.getPost(id).getComments()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var path = req.getPathInfo();
        var body = resp.getWriter();
        if (path == null) {
            createPost(req, resp);
            return;
        }
        var pattern = Pattern.compile("(?<postId>0|[1-9]\\d*)/(?<action>comments|likes)").matcher(path);
        if (!pattern.find()) {
            resp.setStatus(409);
            body.println("Id should be a number");
            return;
        }

        var id = Integer.parseInt(pattern.group("postId"));
        var currUser = (String) getServletConfig().getServletContext().getAttribute("currentUser");
        if (currUser == null) {
            resp.setStatus(401);
            body.println("You should log in first!");
            return;
        }

        Post postToChange;
        try {
            postToChange = Post.getPost(id);
        } catch (IllegalArgumentException exception) {
            resp.setStatus(404);
            body.println("No post with this id was found!");
            return;
        }
        if (currUser.equals(postToChange.getAuthor())) {
            resp.setStatus(409);
            body.println("You cannot comment/like your own post!");
            return;
        }

        var action = pattern.group("action");
        if (action.equals("comments")) {
            var comment = new Comment(req.getParameter("text"), currUser);
            postToChange.addComment(comment);
            resp.setStatus(201);
            body.println(new Gson().toJson(comment));
        } else {  // action == "likes"
            var likes = postToChange.getLikes();
            postToChange.like(currUser);
            if (likes == postToChange.getLikes()) {
                resp.setStatus(409);
                body.println("You have already liked this post before. You cannot like it twice!");
                return;
            }
            resp.setStatus(202);
        }
    }

    public void createPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var users = (HashMap<String, User>) getServletConfig().getServletContext().getAttribute("users");
        var currUser = (String) getServletConfig().getServletContext().getAttribute("currentUser");
        var body = resp.getWriter();
        if (currUser == null) {
            resp.setStatus(401);
            body.println("You are not logged in!");
            return;
        }
        var post = new Post(req.getParameter("picture"), req.getParameter("text"), currUser);
        users.get(currUser).addPost(post);
        resp.setStatus(201);
        body.println(new Gson().toJson(post));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        changeComment(req, resp, (post, commentId) -> post.getComments().get(commentId).setText(req.getParameter("text")));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        changeComment(req, resp, Post::deleteComment);
    }

    private void changeComment(HttpServletRequest req, HttpServletResponse resp, BiConsumer<Post, Integer> changeToMake) throws ServletException, IOException {
        var pattern = Pattern.compile("(?<postId>0|[1-9]\\d*)/comments/(?<commentId>0|[1-9]\\d*)").matcher(req.getPathInfo());
        var body = resp.getWriter();
        if (!pattern.find()) {
            resp.setStatus(409);
            body.println("Post and comment id should be numbers!");
            return;
        }
        var postId = Integer.parseInt(pattern.group("postId"));
        var commentId = Integer.parseInt(pattern.group("commentId"));
        Post post;
        try {
            post = Post.getPost(postId);
        } catch (IllegalArgumentException exception) {
            resp.setStatus(404);
            body.println("No post with this id was found!");
            return;
        }
        Comment commentToChange;
        try {
            commentToChange = post.getComment(commentId);
        } catch (IllegalArgumentException exception) {
            resp.setStatus(404);
            body.println("No comment with this id was found!");
            return;
        }
        if (!commentToChange.getAuthor().equals(getServletConfig().getServletContext().getAttribute("currentUser"))) {
            resp.setStatus(401);
            body.println("You are not the author of this comment and cannot delete it!");
            return;
        }
        changeToMake.accept(post, commentId);
        resp.setStatus(202);
        body.println(new Gson().toJson(commentToChange));
    }
}
