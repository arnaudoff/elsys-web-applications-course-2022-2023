package com.example.instagramhomework;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/uploadPost")
public class Posting extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String photo = request.getParameter ("photoUser");
        String text = request.getParameter("textUser");

        try {
            Connection connection = DatabaseConnection.initializeDatabase();
            PreparedStatement st = connection
                    .prepareStatement("insert into posts(userid,posttext,photoname) values(?,?,?)");

            st.setString(1, "1");
            st.setString(2, text);
            st.setString(3, photo);

            st.executeUpdate();

            st.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        PrintWriter out = response.getWriter();

        out.println("Text: " + text);
        out.println("Photo: " + photo);
        out.close();
    }
}
