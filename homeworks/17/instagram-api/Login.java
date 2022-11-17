package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection connection;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
    

    public void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "root");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	String username   = request.getParameter("username");
    	String password   = String.valueOf(request.getParameter("password"));
        String query = "SELECT * FROM users WHERE username = ? and password = ?";
        PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
	        statement.setString(1, username);
	        statement.setString(2, password);
	        ResultSet rs = statement.executeQuery();
	        //user_id = rs.getInt("id");
	        if(rs.next()) {
	        	if (request.getSession(false) == null) {
	        		request.getSession().setAttribute("user_id", rs.getString("id"));
	        	}
	        }
	        statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
