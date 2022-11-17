package com.example.domashno_;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "FetchComments", value = "/Posts5/*")
public class FetchComments extends HttpServlet {
    Posts postcheck = new Posts();

    Comments commentcheck = new Comments();
    private String PostID;
    private String UserID;
    private String path;


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        path = request.getPathInfo();
        String parts[] = path.split("/");
        PostID = parts[1];
        UserID = (String) request.getSession().getAttribute("ID");

        HttpSession session = request.getSession(false);
        if (session != null) {
            for(int i=0; i<postcheck.archive.size(); i++){
                if(postcheck.archive.get(i).getPostID().equals(PostID)) {
                    int key = i;
                    for (int j = 0; i < commentcheck.comments.size(); j++) {
                        if(postcheck.archive.get(key).getPostID().equals(commentcheck.comments.get(j).getPostID())){
                            response.getWriter().write(commentcheck.comments.get(j).getComment() + " fetched by" + UserID);
                        }

                    }
                }
            }
        } else {
            response.getWriter().write("Sho ne si se lognal");
        }
    }


}
