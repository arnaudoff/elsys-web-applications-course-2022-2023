package com.example.instagram;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "commentsServlet", urlPatterns = {"/comments"})
public class CommentsServlet extends PostsServlet {
    private Gson gson = new Gson();
    private int result = 0;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(isLoggedIn() == false){
            response.sendError(403, "The user is not logged in");
            return;
        }
        try {
            Connection connection = RegisterServlet.getDbConnection();
            String sql = "select * from comments where post_id = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, post_id);
            ResultSet rs=statement.executeQuery();

            while(rs.next()){
                result = 1;
                CommentsJson comment = new CommentsJson(rs.getInt("id"), rs.getString("content"), rs.getInt("user_id"), rs.getInt("post_id"));
                String commentToJson = this.gson.toJson(comment);
                response.addHeader("Comment " + rs.getInt("id"), commentToJson);
            }
            if(result == 0 && rs.wasNull()){
                response.sendError(400, "This post doesn't have comments");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(isLoggedIn() == false){
            resp.sendError(403, "The user is not logged in");
            return;
        }
        if(checkPost(post_id) == false){
            resp.sendError(400, "This post doesn't exist");
        }

        try{
            Connection connection = RegisterServlet.getDbConnection();
            String sql = "insert into comments(content, user_id, post_id)" + " values (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, req.getParameter("content"));
            statement.setInt(2, PostsServlet.user_id);
            statement.setInt(3, PostsServlet.post_id);
            result = statement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }

        if(result == 1){
            resp.sendError(200, "The comment was added successfully");
        }else {
            resp.sendError(400, "Error when uploading the comment");
        }
    }
    public void doPut(HttpServletRequest req, HttpServletResponse resp){
        try{
            if(checkPost(post_id) == false){
                resp.sendError(400, "This post doesn't exist");
            }

            if(checkComment(comment_id) == false){
                    resp.sendError(400, "This comment doesn't exist");
            }
            if(isLoggedIn() == false){
                resp.sendError(403, "The user is not logged in");
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            Connection connection = RegisterServlet.getDbConnection();
            String sql = "update comments set content = ? where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, req.getParameter("content"));
            statement.setInt(2, comment_id);
            result = statement.executeUpdate();

            if(result == 1){
                resp.sendError(200, "Successfully updated comment");
            }else{
                resp.sendError(400, "Error while updating the comment");
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public void doDelete(HttpServletRequest req, HttpServletResponse resp){
        try{
            if(checkPost(post_id) == false){
                    resp.sendError(400, "This post doesn't exist");
            }
            if(checkComment(comment_id) == false){
                    resp.sendError(400, "This comment doesn't exist");
            }
            if(isLoggedIn() == false){
                resp.sendError(403, "The user is not logged in");
                return;
            }
        }catch (Exception e) {
                e.printStackTrace();
        }

        try{
            Connection connection = RegisterServlet.getDbConnection();
            String sql = "delete from comments where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, comment_id);
            result = statement.executeUpdate();

            if(result == 1){
                resp.sendError(200, "Successfully deleted comment");
            }else{
                resp.sendError(400, "Error while updating the comment");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean checkComment(int id){
        try{
            Connection connection = RegisterServlet.getDbConnection();
            String sql = "select * from comments where id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs=statement.executeQuery();
            if(rs.next() == false){
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;

    }
}


