package org.elsys_bg.servlets_hw;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.io.PrintWriter;
import java.util.Map;
import java.util.stream.Collectors;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONObject;


@WebServlet("/posts/*")
public class Posts extends HttpServlet {

	private Connection connection;

	@Override
	public void init() throws ServletException {
		try {
			DataSource ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/instagram_api");
			connection = ds.getConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {     
        PrintWriter pw = resp.getWriter();
        Map<String,String[]> parameterMap = req.getParameterMap();
		resp.setContentType("application/json");

        // /posts?accountId=
        if (parameterMap.containsKey("accountId") && parameterMap.size() == 1) {
			try {
				PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM posts WHERE user_id = ?"
				);
				statement.setInt(1, Integer.parseInt(parameterMap.get("accountId")[0]));
				ResultSet rs = statement.executeQuery();

				JSONObject posts = new JSONObject();
				while (rs.next()) {
					posts.put(rs.getString("id"),new JSONObject()
						.put("user_id", rs.getInt("user_id"))
						.put("picture", rs.getString("picture"))
						.put("description", rs.getString("description"))
					);
				}

				pw.println(posts);
		
				statement.close();
	
			} catch (Exception e) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}

        // /posts/{id}/comments
        } else if (req.getPathInfo() != null && req.getPathInfo().matches("/(0|[1-9]\\d*)/comments")) {
            String[] tokens = req.getPathInfo().split("/");

			try {
				PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM post_comments WHERE post_id = ?;"
				);
				statement.setInt(1, Integer.parseInt(tokens[1]));
				ResultSet rs = statement.executeQuery();

				JSONObject comments = new JSONObject();
				while (rs.next()) {
					comments.put(rs.getString("id"), new JSONObject()
						.put("post_id", rs.getInt("post_id"))
						.put("user_id", rs.getString("user_id"))
						.put("comment", rs.getString("comment"))
					);
				}

				pw.println(comments);
		
				statement.close();
	
			} catch (Exception e) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}

        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
	}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check if the user is logged in
		if (req.getSession(false) == null) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

        // /posts
        if (req.getPathInfo() == null) {
            BufferedReader br = req.getReader();
            String bodyString = br.lines().collect(Collectors.joining());
            JSONObject body = new JSONObject(bodyString);

			if (!body.has("picture") || !body.has("description")) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			try {
				PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO posts VALUES(?, ?, ?, ?)"
				);
		
				statement.setNull(1, Types.INTEGER);
				statement.setInt(2, Integer.parseInt(req.getSession().getAttribute("user_id").toString()));
				statement.setString(3, body.getString("picture"));
				statement.setString(4, body.getString("description"));
		
				statement.executeUpdate();
		
				statement.close();
		
				System.out.println("Successfully posted!");
	
			} catch (Exception e) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}

        // /posts/{id}/comments
        } else if (req.getPathInfo() != null && req.getPathInfo().matches("/(0|[1-9]\\d*)/comments")) {
            String[] tokens = req.getPathInfo().split("/");
            BufferedReader br = req.getReader();
            String bodyString = br.lines().collect(Collectors.joining());
            JSONObject body = new JSONObject(bodyString);

			if (!body.has("comment")) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			try {
				PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO post_comments VALUES(?, ?, ?, ?)"
				);
		
				statement.setNull(1, Types.INTEGER);
				statement.setInt(2, Integer.parseInt(tokens[1]));
				statement.setInt(3, Integer.parseInt(req.getSession().getAttribute("user_id").toString()));
				statement.setString(4, body.getString("comment"));
		
				statement.executeUpdate();
		
				statement.close();
		
				System.out.println("Successfully commented!");
	
			} catch (Exception e) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}

        // /posts/{id}/likes
        } else if (req.getPathInfo() != null && req.getPathInfo().matches("/(0|[1-9]\\d*)/likes")) {
            String[] tokens = req.getPathInfo().split("/");

			try {
				PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO post_likes VALUES(?, ?)"
				);
		
				statement.setInt(1, Integer.parseInt(tokens[1]));
				statement.setInt(2, Integer.parseInt(req.getSession().getAttribute("user_id").toString()));
		
				statement.executeUpdate();
		
				statement.close();
		
				System.out.println("Successfully liked!");
	
			} catch (Exception e) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}

        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check if the user is logged in
		if (req.getSession(false) == null) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

        // /posts/{id}/comments/{id}
        if (req.getPathInfo().matches("/(0|[1-9]\\d*)/comments/(0|[1-9]\\d*)")) {
            String[] tokens = req.getPathInfo().split("/");
            BufferedReader br = req.getReader();
            String bodyString = br.lines().collect(Collectors.joining());
            JSONObject body = new JSONObject(bodyString);

			if (!body.has("comment")) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			try {
				PreparedStatement statement = connection.prepareStatement(
					"SELECT pc.user_id FROM post_comments pc WHERE pc.id = ?"
				);
				statement.setInt(1, Integer.parseInt(tokens[3]));
				ResultSet rs = statement.executeQuery();

				if (rs.next() && !req.getSession().getAttribute("user_id").toString().equals(rs.getString("user_id"))) {
					resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}

				statement = connection.prepareStatement(
					"UPDATE post_comments SET comment = ? WHERE id = ?"
				);
				statement.setString(1, body.getString("comment"));
				statement.setInt(2, Integer.parseInt(tokens[3]));
				statement.executeUpdate();
		
				statement.close();
		
				System.out.println("Successfully updated comment!");
	
			} catch (Exception e) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}

        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check if the user is logged in
		if (req.getSession(false) == null) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

        // /posts/{id}/comments/{id}
        if (req.getPathInfo().matches("/(0|[1-9]\\d*)/comments/(0|[1-9]\\d*)")) {
            String[] tokens = req.getPathInfo().split("/");

			try {
				PreparedStatement statement = connection.prepareStatement(
					"SELECT pc.user_id FROM post_comments pc WHERE pc.id = ?"
				);
				statement.setInt(1, Integer.parseInt(tokens[3]));
				ResultSet rs = statement.executeQuery();

				if (rs.next() && !req.getSession().getAttribute("user_id").toString().equals(rs.getString("user_id"))) {
					resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}

				statement = connection.prepareStatement(
					"DELETE FROM post_comments WHERE id = ?"
				);
				statement.setInt(1, Integer.parseInt(tokens[3]));
				statement.executeUpdate();
		
				statement.close();
		
				System.out.println("Successfully deleted comment!");
	
			} catch (Exception e) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}

        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
