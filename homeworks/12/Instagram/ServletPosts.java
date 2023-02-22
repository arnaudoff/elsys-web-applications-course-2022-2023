package org.elsys.hwservlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ServletPosts", value = "/posts/*")
public class ServletPosts extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        String[] path_split = path.split("/");

        HttpSession session = request.getSession();
        if (session == null) {
            response.setStatus(401);
            return;
        }
        User user = (User) session.getAttribute("User");
        if (!path_split[2].equals("comments")) {
            response.setStatus(400);
            return;
        }
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } finally {
            reader.close();
        }
        JsonObject convertedObject = new Gson().fromJson(String.valueOf(sb), JsonObject.class);
        String newComment = convertedObject.get("comment").getAsString();

        int id = Integer.parseInt(path_split[1]);
        for (Map.Entry<Post, User> set : Post.getPostUserHashMap().entrySet()) {
            if(set.getKey().getId() == id){
                set.getKey().addComment(newComment, user);
                return;
            }
        }
        response.setStatus(400);
    }
}
