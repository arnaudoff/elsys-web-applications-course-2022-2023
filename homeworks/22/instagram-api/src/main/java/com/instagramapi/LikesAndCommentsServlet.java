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

@WebServlet(name = "LikesAndCommentsServlet", value = "/posts/*")
public class LikesAndCommentsServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        if(Utils.initRequest(request, response)){
            return;
        }
        String path = request.getPathInfo();
        String[] path_split = path.split("/");
        if(path_split.length < 3){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Incorrect number of fields provided.");
            return;
        }
        if(!path_split[2].equals("comments")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Incorrect field provided.");
            return;
        }

        UUID postId = UUID.fromString(path_split[1]);
        UUID accountId = (UUID) request.getSession().getAttribute("accountId");

        String commentId;
        String newComment;
        try{
            commentId = path_split[3];
            JsonObject obj = Utils.readJson(request);
            newComment = obj.get("newComment").toString().replace("\"", "");
        }catch (Exception ignored){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Incorrect field provided.");
            return;
        }
        if(newComment.isEmpty()){
            Utils.setErrorOrSuccessMessage(response, "Cannot change message to empty.");
            return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/instagram-api", Utils.database_user, Utils.database_pass);
            Statement st = conn.createStatement();
            String query = String.format(
                    "UPDATE comments SET comment = '%s', editedAt = CURRENT_TIMESTAMP() WHERE id = '%s' AND postId = '%s' AND accountId = '%s'",
                    newComment, commentId, postId, accountId
            );
            st.executeUpdate(query);
            st.close();
            conn.close();
            Utils.setErrorOrSuccessMessage(response, "Successfully changed comment!");
        } catch (Exception ignored) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Error in database query.");
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        if(Utils.initRequest(request, response)){
            return;
        }
        String path = request.getPathInfo();
        String[] path_split = path.split("/");
        if(path_split.length < 3){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Incorrect number of fields provided.");
            return;
        }
        if(!path_split[2].equals("comments")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Incorrect field provided.");
            return;
        }

        UUID postId = UUID.fromString(path_split[1]);
        UUID accountId = (UUID) request.getSession().getAttribute("accountId");

        String commentId = path_split[3];
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/instagram-api", Utils.database_user, Utils.database_pass);
            Statement st = conn.createStatement();
            String query = String.format(
                    "SELECT EXISTS(SELECT * FROM comments WHERE id = '%s' AND postId = '%s' AND accountId = '%s') as comm_exists",
                    commentId, postId, accountId
            );
            ResultSet rs = st.executeQuery(query);
            rs.next();
            if(!rs.getBoolean("comm_exists")){
                Utils.setErrorOrSuccessMessage(response, "Wrong user or comment/post with given id does not exist!");
                return;
            }
            rs.close();
            query = String.format(
                    "DELETE FROM comments WHERE id = '%s' AND postId = '%s' AND accountId = '%s'",
                    commentId, postId, accountId
            );
            st.executeUpdate(query);
            st.close();
            conn.close();
            Utils.setErrorOrSuccessMessage(response, "Successfully deleted comment!");
        } catch (Exception ignored) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Error in database query.");
        }

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Utils.initRequest(request, response)){
            return;
        }
        String path = request.getPathInfo();
        String[] path_split = path.split("/");
        if(path_split.length < 3){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Incorrect number of fields provided.");
            return;
        }

        if(!path_split[2].equals("comments")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Incorrect field provided.");
            return;
        }

        UUID postId = UUID.fromString(path_split[1]);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/instagram-api", Utils.database_user, Utils.database_pass);
            Statement st = conn.createStatement();
            String query = String.format(
                    "SELECT EXISTS(SELECT * FROM comments WHERE postId = '%s') as comm_exists",
                    postId
            );
            ResultSet rs = st.executeQuery(query);
            rs.next();
            if(!rs.getBoolean("comm_exists")){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                Utils.setErrorOrSuccessMessage(response, "Post with given id does not exist or has no comments!");
                return;
            }
            rs.close();
            query = String.format(
                    "SELECT id, comment, username, createdAt, editedAt FROM comments WHERE postId='%s'",
                    postId
            );
            rs = st.executeQuery(query);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JsonObject obj = new JsonObject();
            obj.addProperty("fetch type", "Comments");
            obj.addProperty("postId", postId.toString());
            obj.addProperty("requested by", request.getSession().getAttribute("accountId").toString());
            JsonObject commentsObj = new JsonObject();
            while(rs.next()){
                String user= rs.getString("username");
                String comment = rs.getString("comment");
                String commentId = rs.getString("id");
                String created = rs.getString("createdAt");
                String edited = rs.getString("editedAt");
                JsonObject commentObj = new JsonObject();
                commentObj.addProperty("user", user);
                commentObj.addProperty("comment", comment);
                commentObj.addProperty("created_at", created);
                if(edited != null && !edited.isEmpty()){
                    commentObj.addProperty("edited_at", edited);
                }
                commentsObj.add(commentId,commentObj);
            }
            obj.add("comments", commentsObj);
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
        String path = request.getPathInfo();
        String[] path_split = path.split("/");
        UUID postId = UUID.fromString(path_split[1]);
        UUID accountId = (UUID) request.getSession().getAttribute("accountId");
        if(path_split.length < 3){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Incorrect number of fields provided.");
            return;
        }
        if(path_split[2].equals("likes")){
            doLike(postId, accountId, response);
        }
        else if(path_split[2].equals("comments")){
            String comment;
            try{
                JsonObject obj = Utils.readJson(request);
                comment = obj.get("comment").toString().replace("\"", "");
            }catch (Exception ignored){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                Utils.setErrorOrSuccessMessage(response, "Incorrect field provided.");
                return;
            }
            doComment(postId, accountId, comment, response);
        }
    }

    private void doComment(UUID postId, UUID accountId, String comment, HttpServletResponse response) throws IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/instagram-api", Utils.database_user, Utils.database_pass);
            Statement st = conn.createStatement();
            Random rand = new Random();
            int n = rand.nextInt();
            UUID commentId = UUID.nameUUIDFromBytes(String.format("%s%d", comment, n).getBytes());
            String query = String.format(
              "SELECT username FROM Users WHERE id = '%s'",
                    accountId
            );
            ResultSet rs = st.executeQuery(query);
            rs.next();
            String username = rs.getString("username");
            rs.close();
            query = String.format(
                    "INSERT INTO comments(id, accountId, postId, comment, username, createdAt) VALUES('%s', '%s', '%s', '%s', '%s', CURRENT_TIMESTAMP())",
                    commentId, accountId, postId, comment, username
            );
            st.executeUpdate(query);
            st.close();
            conn.close();
            Utils.setErrorOrSuccessMessage(response, String.format("Successfully created comment with id -  %s", commentId));
        } catch (Exception ignored) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Error in database query.");
        }


    }

    private void doLike(UUID postId, UUID accountId, HttpServletResponse response) throws IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/instagram-api", Utils.database_user, Utils.database_pass);
            String checkQuery = String.format(
                    "SELECT EXISTS(SELECT * FROM Likes WHERE postId='%s' AND accountId='%s') AS liked",postId, accountId
            );
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(checkQuery);
            rs.next();
            if(rs.getBoolean("liked")){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                Utils.setErrorOrSuccessMessage(response, "Post already liked!");
                return;
            }
            else{
                String query = String.format(
                        "INSERT INTO likes(accountId, postId) VALUES('%s', '%s');",
                        accountId, postId
                );
                st.executeUpdate(query);
                query = String.format(
                        "UPDATE posts SET likes = likes + 1 WHERE id = '%s'",
                        postId
                );
                st.executeUpdate(query);
            }
            rs.close();
            st.close();
            conn.close();
            Utils.setErrorOrSuccessMessage(response, "Successfully liked post!");
        } catch (Exception ignored) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Utils.setErrorOrSuccessMessage(response, "Error in database query.");
        }
    }

}

