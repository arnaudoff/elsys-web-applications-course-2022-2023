package com.example.domashno_;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "FetchPosting", value = "/Posts4")
public class FetchPosts extends HttpServlet {
    Posts postcheck = new Posts();
    private String UserID;
    private String UserID2;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        UserID = request.getParameter("accountId");
        UserID2 = (String) request.getSession().getAttribute("ID");
        HttpSession session = request.getSession(false);
        if (session != null) {
           for(int i=0; i<postcheck.archive.size(); i++){
               if(postcheck.archive.get(i).getUserID().equals(UserID)){
                   response.getWriter().write(postcheck.archive.get(i).getPicture()+"  "+ postcheck.archive.get(i).getDescription() +" fetched by User with ID" + UserID2);
               }
           }
        } else {
            response.getWriter().write("Sho ne si se lognal");
        }
    }


}
