package elsys.instagram.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/posts/*")
public class PostServlet extends HttpServlet { 
    
    private Gson gson;

    @Override
    public void init() throws ServletException {
        gson = new Gson();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletContext().getContextPath();
        PrintWriter out = resp.getWriter();
        String username = req.getParameter("username");
        JsonObject jsObj = new JsonObject(); 

        if (path.matches("posts$")) {
            for (Post post: Post.getPosts().values()) {
                if (post.getUsername() == username) {
                    jsObj.addProperty("post_id", post.getPostId());
                }
            }
        } else if (path.matches("posts/(0|[0-9]+)/comments")) {
            // check if posts exists
            String[] ids = path.split("[0-9]+", 1);
            int postId = Integer.parseInt(ids[0]);
            
            try {
                Post post = Post.getPost(postId);
                // get comments
                for (Comment comment: post.getComments().values()) {
                    jsObj.addProperty(Integer.toString(comment.getCommentId()), comment.getComment());
                }
            } catch (InvalidPostIdException e) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
                return;
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        
        resp.setContentType("application/json");
        out.print(jsObj);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletContext().getContextPath();
        PrintWriter out = resp.getWriter();
        User user;

        try {
            user = User.getUser(req.getParameter("username"));
        } catch (InvalidUsernameException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        JsonObject jsObj = gson.toJsonTree(user).getAsJsonObject();

        jsObj.addProperty("username", user.getUsername());
        // /path
        if (path.matches("posts$")) { 
            String description = req.getParameter("description");
            String filePath = req.getParameter("file_path");
            
            Post newPost = new Post(user.getUsername(), description, filePath);
            Post.addPost(newPost);
            
            jsObj.addProperty("description", description);
            jsObj.addProperty("file_path", filePath);

        } else if (path.matches("posts/(0|[0-9]+)/comments$")) {
            // check if posts exists
            String[] ids = path.split("[0-9]+", 1);
            int postId = Integer.parseInt(ids[0]);
            
            try {
                Post post = Post.getPost(postId);
                // get comment
                String commentMsg = req.getParameter("comment");
                Comment comment = new Comment(user, commentMsg);

                jsObj.addProperty("post_id", post.getPostId());
                jsObj.addProperty("comment_id", comment.getCommentId());
                jsObj.addProperty("comment", comment.getComment());
                jsObj.addProperty("commenter", comment.getUser().getUsername());
            } catch (InvalidPostIdException e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());  
            }

        } else if (path.matches("posts/(0|[0-9]+)/likes$")) {
            // check if post exists
            String[] ids = path.split("[0-9]+", 1);
            int postId = Integer.parseInt(ids[0]);

            try {
                Post post = Post.getPost(postId);

                post.like(user);
                jsObj.addProperty("like", "true");
                jsObj.addProperty("liked_by", user.getUsername());
            } catch (InvalidPostIdException e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());  
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

        
        resp.setContentType("application/json");
        out.print(jsObj);
        out.flush();
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String path = req.getServletContext().getContextPath();

        if (path.matches("posts/(0|[0-9]+)/comments/(0|[0-9]+)$")) {
            // check if post exists
            String[] ids = path.split("[0-9]+", 2);
            int postId = Integer.parseInt(ids[0]);
            int commentId = Integer.parseInt(ids[1]);
            String newMsg = req.getParameter("new_msg");
            try {
                Post post = Post.getPost(postId);
                post.getComments().get(commentId).changeComment(User.getUser(post.getUsername()), newMsg);
            } catch (InvalidPostIdException e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());  
            } catch (InvalidUsernameException e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());  
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);  
    
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletContext().getContextPath();

        if (path.matches("posts/(0|[0-9]+)/comments/(0|[0-9]+)$")) {
            // check if post exists
            String[] ids = path.split("[0-9]+", 2);
            int postId = Integer.parseInt(ids[0]);
            int commentId = Integer.parseInt(ids[1]);

            try {
                Post post = Post.getPost(postId);
                post.getComments().remove(commentId);
            } catch (InvalidPostIdException e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());  
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);  
        }
    }
    
}