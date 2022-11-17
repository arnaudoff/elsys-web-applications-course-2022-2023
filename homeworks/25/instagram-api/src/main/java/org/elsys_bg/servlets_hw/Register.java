package org.elsys_bg.servlets_hw;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.stream.Collectors;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONObject;


@WebServlet("/register")
public class Register extends HttpServlet {

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

		if (!body.has("first_name") || !body.has("last_name") ||
			!body.has("username") || !body.has("password")) {
			
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		try {
			PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO users VALUES(?, ?, ?, ?, ?)"
			);
	
			statement.setNull(1, Types.INTEGER);
			statement.setString(2, body.getString("first_name"));
			statement.setString(3, body.getString("last_name"));
			statement.setString(4, body.getString("username"));
			// (should be encrypted, didn't have enough time)
			statement.setString(5, body.getString("password"));
	
			statement.executeUpdate();
	
			statement.close();
			// connection.close();
	
			System.out.println("Successfully registered!");

		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}
}
