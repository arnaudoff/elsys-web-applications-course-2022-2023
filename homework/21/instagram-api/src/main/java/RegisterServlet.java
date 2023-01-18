import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap users = (HashMap) getServletConfig().getServletContext().getAttribute("users");
        String username = req.getParameter("username");
        PrintWriter body = resp.getWriter();
        if (!users.containsKey(username)) {
            User user = new User(req.getParameter("firstName"), req.getParameter("lastName"), username, req.getParameter("password"));
            users.put(username, user);
            resp.setStatus(201);
            body.println(user);
        } else {
            resp.setStatus(409);
            body.println("User with this username is already registered!");
        }
    }
}
