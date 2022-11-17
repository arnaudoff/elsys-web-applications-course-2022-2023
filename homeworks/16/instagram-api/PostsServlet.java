import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

@WebServlet(urlPatterns="/posts/*", loadOnStartup=1)

public class PostsServlet extends HttpServlet {

    @Override

    public void init() throws ServletException {

        getServletConfig().getServletContext().setAttribute("users", new HashMap<String, User>());
        super.init();

    }

    @Override

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var path = req.getPathInfo();
        var users = (HashMap<String, User>) getServletConfig().getServletContext().getAttribute("users");
        var body = resp.getWriter();

        if (path == null) {
            
            var username = req.getParameter("UserId");

            if (!users.containsKey(username)) {

                resp.setStatus(404);
                body.println("Username not found");
                return;
            }

            if (username.equals(getServletConfig().getServletContext().getAttribute("currentUser"))) {

                resp.setStatus(409);
                body.println("Can only fetch different users posts");
                return;
            }

            body.println(users.get(username).getPosts());
            return;

        }

        var pattern = Pattern.compile("(?<postId>0|[1-9]\\d*)/comments").matcher(path);

        if (!pattern.find()) {

            resp.setStatus(400);
            body.println("Id is a num");
            return;

        }

        var id = Integer.parseInt(pattern.group("postId"));
        resp.setStatus(200);
        body.println(Post.getPost(id));

    }

    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var path = req.getPathInfo();
        var body = resp.getWriter();
        var users = (HashMap<String, User>) getServletConfig().getServletContext().getAttribute("users");

        if (path == null) {

            var currentUser = (String) getServletConfig().getServletContext().getAttribute("currentUser");

            if (currentUser == null) {
                resp.setStatus(401);
                body.println("Not logged in");
                return;
            }

            var post = new Post(req.getParameter("picture"), req.getParameter("text"), currentUser);
            users.get(currentUser).addPost(post);
            resp.setStatus(201);
            body.println(post);
            return;

        }

        var pattern = Pattern.compile("(?<postId>0|[1-9]\\d*)/(?<action>comments|likes)").matcher(path);
        System.out.println(path);

        if (!pattern.find()) {

            resp.setStatus(400);
            body.println("Id is a num");
            return;
        }

        var id = Integer.parseInt(pattern.group("postId"));
        var currentUser = (String) getServletConfig().getServletContext().getAttribute("currentUser");

        if (currentUser == null) {

            resp.setStatus(401);
            body.println("You are not logged in");
            return;

        }

        var action = pattern.group("action");
        var postToChange = Post.getPost(id);

        if (currentUser.equals(postToChange.getAuthor())) {

            resp.setStatus(409);
            body.println("interaction with your post is unavailable");
            return;

        }

        if (action.equals("comments")) {

            var comment = new Comment(req.getParameter("text"), currentUser);
            postToChange.addComment(comment);
            resp.setStatus(201);
            body.println(comment);

        } else {  

            resp.setStatus(202);

        }
    }

    @Override

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        changeComment(req, resp, (post, commentId) -> post.getComments().get(commentId).setText(req.getParameter("text")));


    }

    @Override

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        changeComment(req, resp, Post::deleteComment);

    }

    private void changeComment(HttpServletRequest req, HttpServletResponse resp, BiConsumer<Post, Integer> changeToMake) throws ServletException, IOException {

        var pattern = Pattern.compile("(?<postId>0|[1-9]\\d*)/comments/(?<commentId>0|[1-9]\\d*)").matcher(req.getPathInfo());
        var body = resp.getWriter();

        if (!pattern.find()) {
            
            resp.setStatus(400);
            body.println("Post and comment are nums");
            return;

        }

        var postId = Integer.parseInt(pattern.group("postId"));
        var commentId = Integer.parseInt(pattern.group("commentId"));
        var post = Post.getPost(postId);
        var commentsOnPost = post.getComments();

        if (commentId < 0 || commentId > commentsOnPost.size()) {

            resp.setStatus(400);
            body.println("Comment id not avaible");
            return;

        }

        var commentToChange = commentsOnPost.get(commentId);

        if (!commentToChange.getAuthor().equals(getServletConfig().getServletContext().getAttribute("currentUser"))) {

            resp.setStatus(401);
            body.println("Cannot be deleted, these are not posted by you");
            return;

        }

        changeToMake.accept(post, commentId);
        resp.setStatus(202);
        body.println(commentToChange);

    }

}
