package com.example.domashno_;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "Posts", value = "/Posts")
public class Posting extends HttpServlet {
    User user = new User();
    Posts post = new Posts();
    private int counter=0;
    private String UserID;
    private String PostID;
    private String picture;
    private String description;
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();


        picture = request.getParameter("picture");
        description = request.getParameter("description");

        HttpSession session = request.getSession(false);
        if (session != null) {
            UserID = (String) request.getSession().getAttribute("ID");
            counter++;
            Posts posts = new Posts(counter, UserID, picture, description);
            PostID = posts.getPostID();
            posts.archive.add(posts);
            response.setStatus(200);
            response.getWriter().write(UserID);
            response.getWriter().write(PostID);

        } else {
            response.getWriter().write("ne si lognat");
        }
    }


}
