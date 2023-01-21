package org.elsys.hwservlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ServletPost", value = "/post")
public class ServletPost extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session == null) {
            response.setStatus(401);
            return;
        }
        int flag = 0;
        int id = Integer.parseInt(request.getParameter("accountId"));
        for (Map.Entry<Post, User> set : Post.getPostUserHashMap().entrySet()) {
            if(set.getValue().getId() == id){
                flag = 1;
                response.getWriter().println(set.getKey().toString());
            }
        }
        if(flag == 0){
            response.setStatus(400);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        String picture = convertedObject.get("picture").getAsString();
        String description = convertedObject.get("description").getAsString();


        HttpSession session = request.getSession();
        if (session == null) {
            response.setStatus(401);
            return;
        }

        User user = (User) session.getAttribute("User");
        Post post = new Post(picture, description);
        Post.addPost(post, user);
    }
}
