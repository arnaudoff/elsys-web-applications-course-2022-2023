import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Register extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
	
		String First_name = request.getParameter("First_name");
		String Last_name = request.getParameter("Last_name");
        String Username = request.getParameter("Username");
		String Pass = request.getParameter("Pass");
		
		try {
		
			// loading drivers for mysql
			Class.forName("com.mysql.jdbc.Driver");
			
			//creating connection with the database
			Connection con = DriverManager.getConnection
						("jdbc:mysql://localhost:3306/servlers_homework","root","27042004");

			PreparedStatement ps = con.prepareStatement
						("insert into registers values(?,?,?,?)");

			ps.setString(1, First_name);
			ps.setString(2, Last_name);
			ps.setString(3, Username);
            ps.setString(4, Pass);
			int i = ps.executeUpdate();
			
			if(i > 0) {
				out.println("You are successfully registered at geeksforgeeks");
			}
		
		}
		catch(Exception se) {
			se.printStackTrace();
		}
	
	}
}
