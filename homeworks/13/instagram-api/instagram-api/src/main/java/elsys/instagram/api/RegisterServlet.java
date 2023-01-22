package elsys.instagram.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.JsonObject;


@WebServlet(name="RegisterServlet", urlPatterns="/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String first_name = req.getParameter("first_name");
        String last_name = req.getParameter("last_name");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            User newUser = new User(first_name, last_name, username, password);
            User.registerUser(newUser);
            JsonObject jsObj = new JsonObject(); 
            jsObj.addProperty("first_name", newUser.getFirstName());
            jsObj.addProperty("last_name", newUser.getLastName());
            jsObj.addProperty("username", newUser.getUsername());
            jsObj.addProperty("password", newUser.getPassword());

            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            out.print(jsObj);
            out.flush();
        } catch (InvalidUsernameException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }
}
