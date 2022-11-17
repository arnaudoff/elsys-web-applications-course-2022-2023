package test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Post
 */
@WebServlet("/posts")
public class Post extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String user_id;
    private String post_id;
    private String comment_id;
    private Connection connection;


       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Post() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
	        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "root");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        response.setContentType("text/html");
        
        try {
	        String query = "SELECT * FROM posts WHERE user_id = ?";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, String.valueOf(user_id));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String pathInfo = request.getPathInfo();
        String[] splittedPath = pathInfo.split("/");
        post_id = splittedPath[1];

        if (splittedPath[2] == "comments") {
            
            try {
                String query = "SELECT * FROM comments WHERE post_id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, post_id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}           
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");

        user_id = request.getSession(false).getAttribute("user_id").toString();
        String image = request.getParameter("image");
        String description = request.getParameter("description");

        try {
            String query = "insert into post(user_id, image, description)" + " values (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user_id);
            statement.setString(2, image);
            statement.setString(3, description);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String pathInfo = request.getPathInfo();
        String[] split = pathInfo.split("/");
        post_id = split[1];

        if (split.length > 2) {
            if (split[2] == "comments") {
                if (split.length > 3) {
                    comment_id = split[3];
                }
                try {
                    String query = "INSERT INTO comments(post_id, description)" + " values (?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, post_id);
                    statement.setString(2, description);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        String pathInfo = request.getPathInfo();
        String[] split = pathInfo.split("/");
        post_id = split[1];

        String description = request.getParameter("description");

        if (split[2] == "comments") {
            comment_id = split[3];
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram", "root", "root");
                String query = "UPDATE comments SET description = ? WHERE comments_id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, description);
                statement.setString(2, comment_id);
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        String pathInfo = request.getPathInfo();
        String[] split = pathInfo.split("/");
        post_id = split[1];

        if (split[2] == "comments") {
            comment_id = split[3];
            try {
                String query = "DELETE FROM comments WHERE comments_id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, comment_id);
				statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
    }
}
