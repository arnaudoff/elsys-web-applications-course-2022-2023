package org.elsys_bg.servlets_hw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.stream.Collectors;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONObject;


@WebServlet("/login")
public class Login extends HttpServlet {

	private Connection connection;

	@Override
	public void init() throws ServletException {
		try {
			// Get a connection from the pool of the database
			DataSource ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/instagram_api");
			connection = ds.getConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BufferedReader br = req.getReader();
		String bodyString = br.lines().collect(Collectors.joining());
		JSONObject body = new JSONObject(bodyString);

		if (!body.has("username") || !body.has("password")) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		try {
			PreparedStatement statement = connection.prepareStatement(
				"SELECT u.id, u.password FROM users u WHERE u.username = ?"
			);
			statement.setString(1, body.getString("username"));
			ResultSet rs = statement.executeQuery();

			// Check if the username and password match
			// (should be encrypted, didn't have enough time)
			if (rs.next() && !body.getString("password").equals(rs.getString("password"))) {
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}

			// Check if a session already exists
			if (req.getSession(false) != null) {
				resp.sendError(HttpServletResponse.SC_CONFLICT);
				return;
			}

			// Create a new session and save the user's id to it
			req.getSession().setAttribute("user_id", rs.getString("id"));

			PrintWriter pw = resp.getWriter();
			resp.setContentType("application/json");
			pw.print(new JSONObject().put("user_id", rs.getString("id")).toString(4));

			System.out.println("User " + body.getString("username") + " logged in!");
	
			statement.close();

		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}
}
