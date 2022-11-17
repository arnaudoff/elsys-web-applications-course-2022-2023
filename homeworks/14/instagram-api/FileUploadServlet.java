package com.example.instagram;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "FileUploadServlet", value = "/posts")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class FileUploadServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<form method=\"post\" action=\"posts\" enctype=\"multipart/form-data\">");
        out.println("Description: <input type=\"text\" name=\"description\" /><br>");
        out.println("<input type=\"file\" name=\"file\" />");
        out.println("<input type=\"submit\" value=\"Upload\" />");
        out.println("</form>");
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String description = request.getParameter("description");
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();

        String relativeWebPath = "WEB-INF/";
        String absoluteFilePath = getServletContext().getRealPath(relativeWebPath);
        System.out.println(getServletContext().getRealPath("WEB-INF/"));
        System.out.println(absoluteFilePath);
        File uploadedFile = new File(absoluteFilePath + fileName);
        System.out.println(uploadedFile.getAbsolutePath());


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "password");
            PreparedStatement preparedStatement = connection.prepareStatement("insert into posts(username, description, fileName) values(?,?,?)");
            String username = request.getSession().getAttribute("username").toString();
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, fileName);
            int status = preparedStatement.executeUpdate();
            if (status != 0) {
                filePart.write(uploadedFile.getAbsolutePath());
            }

            response.getWriter().print("The file uploaded successfully.");
            response.getWriter().print(description);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}