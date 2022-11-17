package com.example.domashno_;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "Changing", value = "/Posts1/*")
public class Changing extends HttpServlet {
    Posts postcheck = new Posts();
    Comments commentcheck = new Comments();
    private String path;
    private String PostID;
    private String comment;
    private String CommentID;


    public boolean check(String PostID, String CommentID){
        for(int i=0; i < postcheck.archive.size(); i++){
            if(postcheck.archive.get(i).getPostID().equals(PostID)){
                for(int j=0; i<commentcheck.comments.size();i++ ){
                    if(commentcheck.comments.get(i).getCommentID().equals(CommentID)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        path = request.getPathInfo();
        String parts[] = path.split("/");
        PostID = parts[1];
        comment = parts[2];
        CommentID = parts[3];


        HttpSession session = request.getSession(false);
        if (session != null && check(PostID, CommentID) == true) {
            for(int i=0; i< commentcheck.comments.size();i++){
                if(commentcheck.comments.get(i).getUserID().equals(request.getSession().getAttribute("ID"))){
                    commentcheck.comments.get(i).setComment(comment);
                }
            }

        } else {
            response.getWriter().write("Nqma Post s takova id ili komentar s takova id ili ne si se lognal");
        }
    }


}
