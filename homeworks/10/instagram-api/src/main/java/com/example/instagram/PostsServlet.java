package com.example.instagram;

import com.google.gson.Gson;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//value = "/posts-servlet",

@WebServlet(name = "postsServlet", urlPatterns = {"/posts/*"})
public class PostsServlet extends LoginServlet{

    private String link;
    private String description;
    protected static int post_id;
    protected static int comment_id;
    private Gson gson = new Gson();
    private boolean changeContext = false;
    private int result = 0;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        if(isLoggedIn() == false){
            response.sendError(403, "The user is not logged in");
            return;
        }

        handlePath(request, response);

        try {
            Connection connection = RegisterServlet.getDbConnection();
            String sql = "select * from posts where user_id = ? ";
            PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            statement.setInt(1, Integer.parseInt(request.getParameter("accountId")));
            ResultSet rs=statement.executeQuery();

            while(rs.next()){
                result = 1;
                PostsJson post = new PostsJson(rs.getInt("id"), rs.getString("link"), rs.getString("description"), rs.getInt("user_id"));
                String postToJson = this.gson.toJson(post);
                response.addHeader("Post " + rs.getInt("id"), postToJson);
            }
            if(rs.next() == false && result == 0){
                response.sendError(400, "This user doesn't have posts or doesn't exist");
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
        handlePath(req, resp);

        if(changeContext){
            changeContext = false;
            return;
        }

        link = req.getParameter("link");
        description = req.getParameter("description");
        try{
            Connection connection = RegisterServlet.getDbConnection();
            String sql = "insert into posts(link, description, user_id)" + " values (?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, link);
            statement.setString(2, description);
            statement.setInt(3, LoginServlet.user_id);
            result = statement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }

        if(result == 1){
            resp.sendError(200, "Successfully added post");
        }else{
            resp.sendError(400, "The post was not added");
        }

    }

    public void doPut(HttpServletRequest request, HttpServletResponse response){
        try{
            if(!isLoggedIn()){
                response.sendError(403, "The user is not logged in");
                return;
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        handlePath(request, response);
        if(changeContext){
            changeContext = false;
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response){
        if(!isLoggedIn()){
            try {
                response.sendError(403, "The user is not logged in");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        handlePath(request, response);
        if(changeContext){
            changeContext = false;
        }
    }

    private void handlePath(HttpServletRequest request, HttpServletResponse response){
        String pathInfo = request.getPathInfo();
        if(pathInfo == null){
            return;
        }
        String[] pathParts = pathInfo.split("/");
        if(pathParts.length < 1){
            return;
        }
        post_id = Integer.parseInt(pathParts[1]);

        if(pathParts.length > 2){
            try{
                if(pathParts[2].equals("comments")){
                    if(pathParts.length > 3){
                        comment_id = Integer.parseInt(pathParts[3]);
                    }
                    changeContext = true;
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/comments");
                    dispatcher.forward(request, response);
                }
                if(pathParts[2].equals("likes")){
                    changeContext = true;
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/likes");
                    dispatcher.forward(request, response);
                }

            }catch (Exception e){
                e.printStackTrace();
            }


        }

    }
    public boolean checkPost(int id){
        try{
            Connection connection = RegisterServlet.getDbConnection();
            String sql = "select * from posts where id = ?";
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
