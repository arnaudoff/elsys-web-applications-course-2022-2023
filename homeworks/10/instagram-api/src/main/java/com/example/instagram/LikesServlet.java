package com.example.instagram;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet(name = "likesServlet", urlPatterns = {"/likes"})

public class LikesServlet extends PostsServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            if(checkPost(post_id) == false){
                    resp.sendError(400, "This post doesn't exist");
                    return;
            }
            if(isLoggedIn() == false){
                resp.sendError(403, "The user is not logged in");
                return;
            }
            String sql = "insert into likes(user_id, post_id)" + " values ( ?, ?)";
            Connection connection = RegisterServlet.getDbConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, PostsServlet.user_id);
            statement.setInt(2, PostsServlet.post_id);
            statement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
