package com.example.demo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "Posting", value = "/posts/*")
public class Posting extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(401);
            return;
        }
        String path = request.getPathInfo();
        String[] path_split = path.split("/");


        if (path_split[2].equals("comments")) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/instagram", "root", "12345678");
                Statement st = conn.createStatement();

                String query = String.format(
                        "SELECT id, text, username FROM coments WHERE post_id='%s'",
                        path_split[1]
                );
                ResultSet rs = st.executeQuery(query);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                JsonObject obj = new JsonObject();
                JsonObject commentsObj = new JsonObject();
                while (rs.next()) {
                    String user = rs.getString("username");
                    String comment = rs.getString("text");
                    String commentId = rs.getString("id");

                    JsonObject commentObj = new JsonObject();
                    commentObj.addProperty("user", user);
                    commentObj.addProperty("comment", comment);

                    commentsObj.add(commentId, commentObj);
                }
                obj.add("comments", commentsObj);
                response.getWriter().write(new Gson().toJson(obj));
                rs.close();
                st.close();
                conn.close();
            } catch (Exception ignored) {
                throw new RuntimeException(ignored);
            }

        }
    }



    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(401);
            return;
        }
        String path = request.getPathInfo();
        String[] path_split = path.split("/");


        try{
            int id = Integer.parseInt(path_split[1]);
            int id2 = Integer.parseInt(path_split[3]);
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        if(path_split[2].equals("comments")){
            StringBuffer jb = new StringBuffer();
            String line = null;
            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null)
                    jb.append(line);
            } catch (Exception e) { /*report an error*/ }


            JsonObject convertedObject = new Gson().fromJson(jb.toString(), JsonObject.class);

            try
            {
                // create a mysql database connection
                String myUrl = "jdbc:mysql://localhost:3306";
                String myDriver = "com.mysql.cj.jdbc.Driver";
                Class.forName(myDriver);

                Connection conn = DriverManager.getConnection(myUrl, "root", "12345678");

                Statement st = conn.createStatement();

                // the mysql insert statement
                String query = String.format(
                        "SELECT username FROM instagram.coments WHERE id='%s'",path_split[3]);

                ResultSet rs = st.executeQuery(query);
                rs.next();
                String dbCommentUsername = rs.getString("username");

                if(dbCommentUsername.equals((String)session.getAttribute("username"))){

                    query = "DELETE FROM instagram.coments WHERE id = ?";
                    PreparedStatement preparedStmt = conn.prepareStatement(query);
                    preparedStmt.setInt (1,Integer.parseInt(path_split[3]));


                    preparedStmt.execute();
                    response.setStatus(200);
                    return;
                }
                System.out.println(dbCommentUsername);



                conn.close();
            }
            catch (Exception e)
            {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }
        }
        response.setStatus(401);


    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(401);
            return;
        }
        String path = request.getPathInfo();
        String[] path_split = path.split("/");


        try{
            int id = Integer.parseInt(path_split[1]);
            int id2 = Integer.parseInt(path_split[3]);
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        if(path_split[2].equals("comments")){
            StringBuffer jb = new StringBuffer();
            String line = null;
            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null)
                    jb.append(line);
            } catch (Exception e) { /*report an error*/ }


            JsonObject convertedObject = new Gson().fromJson(jb.toString(), JsonObject.class);

            try
            {
                // create a mysql database connection
                String myUrl = "jdbc:mysql://localhost:3306";
                String myDriver = "com.mysql.cj.jdbc.Driver";
                Class.forName(myDriver);

                Connection conn = DriverManager.getConnection(myUrl, "root", "12345678");

                Statement st = conn.createStatement();

                // the mysql insert statement
                String query = String.format(
                        "SELECT username FROM instagram.coments WHERE id='%s'",path_split[3]);

                ResultSet rs = st.executeQuery(query);
                rs.next();
                String dbCommentUsername = rs.getString("username");

                if(dbCommentUsername.equals((String)session.getAttribute("username"))){

                    query = "UPDATE instagram.coments t SET t.text = ? WHERE t.id = ?";
                    PreparedStatement preparedStmt = conn.prepareStatement(query);
                    preparedStmt.setString (1, convertedObject.get("text").getAsString());
                    preparedStmt.setInt (2,Integer.parseInt(path_split[3]));


                    preparedStmt.execute();
                    response.setStatus(200);
                    return;
                }
                System.out.println(dbCommentUsername);



                conn.close();
            }
            catch (Exception e)
            {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }
        }
        response.setStatus(403);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        String[] path_split = path.split("/");

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(401);
            return;
        }

        try{
            int id = Integer.parseInt(path_split[1]);
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }

        if(path_split[2].equals("likes")){
            StringBuffer jb = new StringBuffer();
            String line = null;
            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null)
                    jb.append(line);
            } catch (Exception e) { /*report an error*/ }


            JsonObject convertedObject = new Gson().fromJson(jb.toString(), JsonObject.class);

            try
            {
                // create a mysql database connection
                String myUrl = "jdbc:mysql://localhost:3306";
                String myDriver = "com.mysql.cj.jdbc.Driver";
                Class.forName(myDriver);

                Connection conn = DriverManager.getConnection(myUrl, "root", "12345678");

                Statement st = conn.createStatement();



                    String query = "INSERT INTO instagram.likes (post_id, username) " + "VALUES (?,?)";
                    PreparedStatement preparedStmt = conn.prepareStatement(query);
                    preparedStmt.setString (2,(String)session.getAttribute("username") );
                    preparedStmt.setInt (1, Integer.parseInt(path_split[1]));

                    preparedStmt.execute();

                    conn.close();


                conn.close();
            }
            catch (Exception e)
            {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }
        }






        if(path_split[2].equals("comments")){
            StringBuffer jb = new StringBuffer();

            String line = null;
            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null)
                    jb.append(line);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            Gson gson = new Gson();
            Comment comment = gson.fromJson(jb.toString(),Comment.class);





            try
            {
                // create a mysql database connection
                String myUrl = "jdbc:mysql://localhost:3306";
                String myDriver = "com.mysql.cj.jdbc.Driver";
                Class.forName(myDriver);

                Connection conn = DriverManager.getConnection(myUrl, "root", "12345678");

                // the mysql insert statement
                String query = "INSERT INTO instagram.coments (text, post_id, username) " + "VALUES (?,?,?)";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString (1, comment.getText());
                preparedStmt.setInt (2, Integer.parseInt(path_split[1]));
                preparedStmt.setString (3, (String) session.getAttribute("username"));

                preparedStmt.execute();

                conn.close();

            }
            catch (Exception e)
            {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }
            response.setStatus(401);
        }


    }
}
