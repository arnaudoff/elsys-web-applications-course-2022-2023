import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name="PostsServlet", urlPatterns="/posts/*", loadOnStartup=1)
public class PostsServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        getServletConfig().getServletContext().setAttribute("users", new HashMap<String, User>());
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        HashMap<String, User> users = (HashMap<String, User>) getServletConfig().getServletContext().getAttribute("users");
        PrintWriter body = resp.getWriter();
        if (path != null) {
            Matcher pattern = Pattern.compile("(?<postId>0|[1-9]\\d*)/comments").matcher(path);
            if (pattern.find()) {
                int id = Integer.parseInt(pattern.group("postId"));
                resp.setStatus(200);
                body.println(Post.getPost(id));
            } else {
                resp.setStatus(400);
                body.println("Id should be a number");
            }
        } else {
            String username = req.getParameter("accountId");
            if (users.containsKey(username)) {
                if (!username.equals(getServletConfig().getServletContext().getAttribute("currentUser"))) {
                    body.println(users.get(username).getPosts());
                } else {
                    resp.setStatus(409);
                    body.println("Can only fetch other users' posts");
                }
            } else {
                resp.setStatus(404);
                body.println("No user with this username found!");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        PrintWriter body = resp.getWriter();
        HashMap<String, User> users = (HashMap<String, User>) getServletConfig().getServletContext().getAttribute("users");
        if (path != null) {
            Matcher pattern = Pattern.compile("(?<postId>0|[1-9]\\d*)/(?<action>comments|likes)").matcher(path);
            System.out.println(path);
            if (pattern.find()) {
                int id = Integer.parseInt(pattern.group("postId"));
                String currUser = (String) getServletConfig().getServletContext().getAttribute("currentUser");
                if (currUser != null) {
                    String action = pattern.group("action");
                    Post postToChange = Post.getPost(id);
                    if (!currUser.equals(postToChange.getProfile())) {
                        if (!action.equals("comments")) {
                            int likes = postToChange.getLike();
                            postToChange.like(currUser);
                            if (likes != postToChange.getLike()) {
                                resp.setStatus(202);
                            } else {
                                resp.setStatus(409);
                                body.println("You have already liked this post before. You cannot like it twice!");
                            }
                        } else {
                            Comment comment = new Comment(req.getParameter("Description"), currUser);
                            postToChange.addComment(comment);
                            resp.setStatus(201);
                            body.println(comment);
                        }
                    } else {
                        resp.setStatus(409);
                        body.println("You cannot comment/like your own post!");
                    }

                } else {
                    resp.setStatus(401);
                    body.println("You should log in first!");
                }

            } else {
                resp.setStatus(400);
                body.println("Id should be a number");
            }
        } else {
            String currUser = (String) getServletConfig().getServletContext().getAttribute("currentUser");
            if (currUser != null) {
                Post post = new Post(req.getParameter("picture"), req.getParameter("Description"), currUser);
                users.get(currUser).addPost(post);
                resp.setStatus(201);
                body.println(post);
            } else {
                resp.setStatus(401);
                body.println("You are not logged in!");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        changeComment(req, resp, (post, commentId) -> post.getComments().get(commentId).setDescription(req.getParameter("Description")));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        changeComment(req, resp, Post::deleteComment);
    }

    private void changeComment(HttpServletRequest req, HttpServletResponse resp, BiConsumer<Post, Integer> changeToMake) throws ServletException, IOException {
        Matcher pattern = Pattern.compile("(?<postId>0|[1-9]\\d*)/comments/(?<commentId>0|[1-9]\\d*)").matcher(req.getPathInfo());
        PrintWriter body = resp.getWriter();
        if (pattern.find()) {
            int postId = Integer.parseInt(pattern.group("postId"));
            int commentId = Integer.parseInt(pattern.group("commentId"));
            Post post = Post.getPost(postId);
            HashMap<Integer, Comment> commentsOnPost = post.getComments();
            if (commentId < 0 || commentId > commentsOnPost.size()) {
                resp.setStatus(400);
                body.println("Comment id is invalid!");
            } else {
                Comment commentToChange = commentsOnPost.get(commentId);
                if (!commentToChange.getProfile().equals(getServletConfig().getServletContext().getAttribute("currentUser"))) {
                    resp.setStatus(401);
                    body.println("You are not the author of this comment and cannot delete it!");
                } else {
                    changeToMake.accept(post, commentId);
                    resp.setStatus(202);
                    body.println(commentToChange);
                }
            }
        } else {
            resp.setStatus(400);
            body.println("Post and comment id should be numbers!");
        }
    }
}
