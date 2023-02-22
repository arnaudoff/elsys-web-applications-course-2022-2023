package test;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection connection;

    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "root");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String username   = request.getParameter("username");
    	String first_name = request.getParameter("first_name");
    	String last_name  = request.getParameter("last_name");
    	String password   = String.valueOf(request.getParameter("password"));
        String insert = "INSERT INTO users(user_id, first_name, last_name, username, password)" + " values (?, ?, ?, ?, ?)";
        PreparedStatement statement;
        
		try {
			statement = connection.prepareStatement(insert);
			statement.setNull(1, Types.INTEGER);
	        statement.setString(2, first_name);
	        statement.setString(3, last_name);
	        statement.setString(4, username);
	        statement.setString(5, password);
	        statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }
}

