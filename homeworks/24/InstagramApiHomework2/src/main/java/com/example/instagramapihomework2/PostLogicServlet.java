package com.example.instagramapihomework2;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import Models.Comment;
import Models.Like;
import Models.Post;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "postLogicServlet", urlPatterns = {"/posts/*"})
public class PostLogicServlet extends HttpServlet {
    private Gson gson;

    public void init() {
        gson = new Gson();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        HttpSession session = request.getSession(false);
        if(session==null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String path = String.valueOf(request.getRequestURL());
        path = path.substring(path.lastIndexOf("/posts"));

        if(path.equals("/posts") && request.getParameterMap().size() == 1 && request.getParameterMap().containsKey("accountId")){
            int accId = Integer.parseInt(request.getParameter("accountId"));
            try {
                if(!DAO.accountIdExists(accId)){
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                ArrayList<Post> posts = DAO.fetchPosts(accId);
                String postsInJson = gson.toJson(posts);
                PrintWriter out = response.getWriter();
                out.print(postsInJson);
                out.flush();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else if(path.matches("/posts/\\d+/comments")){
            int postId = Integer.parseInt(path.split("/")[2]);
            try {
                if(!DAO.postIdExists(postId)){
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }

                ArrayList<Comment> comments = DAO.fetchComments(postId);
                String commentsInJson = gson.toJson(comments);
                PrintWriter out = response.getWriter();
                out.print(commentsInJson);
                out.flush();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String pathInfo = String.valueOf(request.getPathInfo());
        if (pathInfo.matches("null")) {
            String description = request.getParameter("description");
            Post post = new Post(description);
            try {
                DAO.addPost(String.valueOf(session.getAttribute("username")), post);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else if (pathInfo.matches("/\\d+/comments")) {
            String requestData = request.getReader().lines().collect(Collectors.joining());
            JsonObject convertedObject = new Gson().fromJson(requestData, JsonObject.class);

            String comment = convertedObject.get("comment").getAsString();
            String username = String.valueOf(session.getAttribute("username"));
            int postId = Integer.parseInt(pathInfo.split("/")[1]);
            try {
                if (DAO.postIdExists(postId)) {
                    Comment comment1 = new Comment(postId, username, comment);
                    try {
                        DAO.addComment(comment1);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (pathInfo.matches("/\\d+/likes")) {
            String username = String.valueOf(session.getAttribute("username"));
            int postId = Integer.parseInt(pathInfo.split("/")[1]);
            try {
                if(!DAO.postIdExists(postId)){
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Like like = new Like(username, postId);
            try {
                if(!DAO.likeExists(like)){
                    try {
                        DAO.addLike(like);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    response.setStatus(HttpServletResponse.SC_OK);
                }else{
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }


    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String path = String.valueOf(request.getRequestURL());
        path = path.substring(path.lastIndexOf("/posts"));

        if(path.matches("/posts/\\d+/comments/\\d+")){
            int postId = Integer.parseInt(path.split("/")[2]);
            int commentId = Integer.parseInt(path.split("/")[4]);
            try {
                if(!DAO.postIdExists(postId) | !DAO.commentIdExists(commentId)){
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                if(!DAO.commentIdBelongsToUser(String.valueOf(session.getAttribute("username")), commentId)){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                DAO.deleteComment(commentId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
    }
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String path = String.valueOf(request.getRequestURL());
        path = path.substring(path.lastIndexOf("/posts"));

        if(path.matches("/posts/\\d+/comments/\\d+")){
            int postId = Integer.parseInt(path.split("/")[2]);
            int commentId = Integer.parseInt(path.split("/")[4]);
            try {
                if(!DAO.postIdExists(postId) | !DAO.commentIdExists(commentId)){
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                if(!DAO.commentIdBelongsToUser(String.valueOf(session.getAttribute("username")), commentId)){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                String requestData = request.getReader().lines().collect(Collectors.joining());
                JsonObject convertedObject = new Gson().fromJson(requestData, JsonObject.class);
                String newDesc = convertedObject.get("new_description").getAsString();
                DAO.updateComment(commentId, newDesc);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    public void destroy() {
    }
}
