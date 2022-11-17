package com.example.domashno_;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "Liking", value = "/Posts3/*")
public class Liking extends HttpServlet {
    Posts postcheck = new Posts();
    Likes like = new Likes();
    private String path;
    private String PostID;
    private String comment;
    private int counter=0;


    public boolean check(String PostID, String UserID){
        for(int i=0; i < postcheck.archive.size(); i++){
            if(postcheck.archive.get(i).getPostID().equals(PostID)){
                int key = i;
                for(int j=0; j<like.likes.size(); j++){
                    if(like.likes.get(j).getPostID().equals(postcheck.archive.get(key).getPostID()) && like.likes.get(j).getUserID().equals(postcheck.archive.get(key).getUserID())) {
                        return false;
                    }
                        if(like.likes.get(j).getPostID().equals(postcheck.archive.get(key).getPostID()) && like.likes.get(j).getUserID().equals(UserID)){
                            return false;
                    }

                }
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
        if(parts[2] !="Liking"){
            response.setStatus(200);
        }

        HttpSession session = request.getSession(false);
        String UserID = (String) request.getSession().getAttribute("ID");
        if (session != null && check(PostID, UserID) == true) {
            counter++;
            Likes like = new Likes(counter,PostID, UserID);
            like.likes.add(like);
        } else {
            response.getWriter().write("Sho ne si se lognal/ Nema takav Post / Ne mojesh da laikvash dva puti");
        }
    }


}
