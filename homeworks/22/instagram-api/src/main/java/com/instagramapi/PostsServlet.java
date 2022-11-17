package com.instagramapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.Random;
import java.util.UUID;

@WebServlet(name = "PostsServlet", value = "/posts")
public class PostsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Utils.initRequest(request, response)){
            return;
        }

        UUID accountId;
        try{
            accountId = UUID.fromString(request.getParameter("accountId"));
        }catch(Exception ignored){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Incorrect field passed.");
            return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/instagram-api", Utils.database_user, Utils.database_pass);
            Statement st = conn.createStatement();
            String query = String.format(
                    "SELECT EXISTS(SELECT * FROM posts WHERE accountId = '%s') as posts_exist",
                    accountId
            );
            ResultSet rs = st.executeQuery(query);
            rs.next();
            if(!rs.getBoolean("posts_exist")){
                Utils.setErrorOrSuccessMessage(response, "User has no posts yet!");
                return;
            }
            rs.close();
            query = String.format(
                    "SELECT link, description, id, createdAt, likes FROM posts WHERE accountId='%s'",
                    accountId
            );
            rs = st.executeQuery(query);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JsonObject obj = new JsonObject();
            obj.addProperty("fetch type", "Posts");
            obj.addProperty("posts by", accountId.toString());
            obj.addProperty("requested by", request.getSession().getAttribute("accountId").toString());
            JsonObject postsObj = new JsonObject();
            while(rs.next()){
                String link = rs.getString("link");
                String description = rs.getString("description");
                String postId = rs.getString("id");
                String created = rs.getString("createdAt");
                String likes = rs.getString("likes");
                JsonObject postObj = new JsonObject();
                postObj.addProperty("image_link", link);
                if(description != null && !description.isEmpty()){
                    postObj.addProperty("description", description);
                }
                postObj.addProperty("likes", likes);
                postObj.addProperty("created_at", created);
                postsObj.add(postId, postObj);
            }
            obj.add("posts", postsObj);
            response.getWriter().write(new Gson().toJson(obj));
            rs.close();
            st.close();
            conn.close();
        } catch (Exception ignored) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Error in database query.");
        }


    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Utils.initRequest(request, response)){
            return;
        }
        String image_link;
        String description = null;
        UUID id;
        UUID accountId = (UUID) request.getSession().getAttribute("accountId");
        try{
            JsonObject obj = Utils.readJson(request);
            image_link =  obj.get("image_link").toString().replace("\"", "");
            try{
                description = obj.get("description").toString().replace("\"", "");
            }catch(Exception ignored){}
        }catch(Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Incorrect field passed.");
            return;
        }
        Random rand = new Random();
        int n = rand.nextInt();
        id = UUID.nameUUIDFromBytes(String.format("%s%d", image_link, n).getBytes());
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/instagram-api", Utils.database_user, Utils.database_pass);
            String query;
            if(description == null){
                query = String.format(
                        "INSERT INTO posts (id,  accountId, link, createdAt) " +
                                "VALUES('%s', '%s', '%s', CURRENT_TIMESTAMP())",
                        id, accountId, image_link
                );
            }
            else{
                query = String.format(
                        "INSERT INTO posts (id,  accountId, link, description, createdAt) " +
                                "VALUES('%s', '%s', '%s', '%s', CURRENT_TIMESTAMP())",
                        id, accountId, image_link, description
                );
            }
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            st.close();
            conn.close();
            Utils.setErrorOrSuccessMessage(response, String.format("Successfully created post!<br>Post id is -  %s", id));
        } catch (SQLException | ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Error in database query.");
        }

    }
}
