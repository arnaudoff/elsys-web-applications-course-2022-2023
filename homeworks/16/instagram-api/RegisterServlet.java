import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/register")

public class RegisterServlet extends HttpServlet {

    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        var users = (HashMap) getServletConfig().getServletContext().getAttribute("users");
        var username = req.getParameter("username");
        var body = resp.getWriter();

        if (users.containsKey(username)) {
            resp.setStatus(409);
            body.println("Name in use");
                return;
        }

        var user = new User(req.getParameter("first"), req.getParameter("last"), username, req.getParameter("password"));
        users.put(username, user);
        resp.setStatus(201);
        body.println(user);

    }
}
