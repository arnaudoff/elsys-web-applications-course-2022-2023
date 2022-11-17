package elsys.instagram.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        PrintWriter out = resp.getWriter();

        try {
            User user = User.getUser(username);

            if (user.getPassword() != password) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Wrong password");
            }

            String userJson = gson.toJson(user);
            resp.setContentType("application/json");
            out.print(userJson);
        } catch (InvalidUsernameException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } finally {
            out.flush();
        }

    }
}
