package com.example.instagramhw.endpoints;

import com.example.instagramhw.models.Post;
import com.example.instagramhw.models.ReadRequest;
import com.example.instagramhw.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "PostServlet", value = "/posts")
public class PostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user;
        if (session != null) {
            // a session exists
            user = (User) session.getAttribute("user");
        } else {
            response.setStatus(401);
            return;
        }

        String data = ReadRequest.readRequest(request);
        JsonObject convertedObject = new Gson().fromJson(data, JsonObject.class);
        String picture = convertedObject.get("picture").getAsString();
        String description = convertedObject.get("description").getAsString();

        Post post = new Post(description, picture, user.getAccountId());
        user.makePost(post);
        Post.addPost(post);
        response.getWriter().println("You successfully add post with picture: " + picture + " and description: " + description);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user;
        if (session != null) {
            // a session exists
            user = (User) session.getAttribute("user");
        } else {
            resp.setStatus(401);
            return;
        }

        int accountId = Integer.parseInt(req.getParameter("accountId"));
        for(User user1 : User.getUsers()){
            if(user1.getAccountId() == accountId){
                if(user1.getPosts().size() == 0){
                    resp.getWriter().println("User with given id don't have any posts.");
                    return;
                }
                for(Post post : user1.getPosts()){
                    resp.getWriter().println("Posts: \n");
                    resp.getWriter().println(post.print());
                }
                return;
            }
        }
        resp.setStatus(400);
    }
}
