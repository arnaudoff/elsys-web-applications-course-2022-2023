import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, User> users = (HashMap<String, User>) getServletConfig().getServletContext().getAttribute("users");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (!users.containsKey(username)) {
            resp.setStatus(404);
            resp.getWriter().println("No user with this username was found!");
        } else if (password.equals(users.get(username).getPassword())) {
            getServletConfig().getServletContext().setAttribute("currentUser", username);
        } else {
            resp.setStatus(409);
            resp.getWriter().println("Incorrect password!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, User> users = (HashMap<String, User>) getServletConfig().getServletContext().getAttribute("users");
        for (User user : users.values()) {
            resp.getWriter().println(user.getFirstName() + " " + user.getLastName() + " as " + user.getUsername() + ": " + user.getPassword());
        }
        resp.getWriter().println(getServletConfig().getServletContext().getAttribute("currentUser"));
    }
}
