package com.example.domashno_;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "Commenting", value = "/Posts/*")
public class Commenting extends HttpServlet {
    Posts postcheck = new Posts();
    private String path;
    private String PostID;
    private String comment;
    private int counter=0;


    public boolean check(String PostID){
        for(int i=0; i < postcheck.archive.size(); i++){
            if(postcheck.archive.get(i).getPostID().equals(PostID)){
                return true;
            }
        }
        return false;
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        path = request.getPathInfo();
        String parts[] = path.split("/");
        PostID = parts[1];
        comment = parts[2];
        HttpSession session = request.getSession(false);
        if (session != null && check(PostID) == true) {
            String UserID = (String) request.getSession().getAttribute("ID");
            counter++;
            Comments com = new Comments(counter,PostID, UserID, comment);
            com.comments.add(com);
        } else {
            response.getWriter().write("Sho ne si se lognal/Ne si postnal");
        }
    }


}
