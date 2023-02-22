package elsys.instagram.api;

import java.util.HashMap;


class InvalidPostIdException extends Exception {
    InvalidPostIdException(String str) {
        super(str);
    }
}

public class Post {
    private static HashMap<Integer, Post> posts = new HashMap<Integer, Post>();

    private static int idCounter = 0;

    private String username;    
    private String description;
    private String imgPath;
    private HashMap<Integer, Comment> comments;
    private HashMap<String, Boolean> likes;
    private int postId;

    Post(String username, String description, String imgPath) {
        this.comments = new HashMap<Integer, Comment>();
        this.likes= new HashMap<String, Boolean>();
        this.username = username;
        this.description = description;
        this.imgPath = imgPath;
        this.postId = Post.idCounter++;
    }
    
    public static Post getPost(int id) throws InvalidPostIdException {
        if (!Post.exists(id)) {
            throw new InvalidPostIdException("Post doesn't exist");
        }

        return posts.get(id);
    }
    
    public static boolean exists(int id) {
        return posts.containsKey(id);
    }
    
    public static void addPost(Post post) {
        posts.put(post.getPostId(), post);
    }

    public void like(User username) {
        likes.put(username.getUsername(), true);
    }

    public void addComment(Comment comment) {
        comments.put(comment.getCommentId(), comment);
    }

    public String getDescription() {
        return description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getUsername() {
        return username;
    }
    
    public int getPostId() {
        return postId;
    }

    public HashMap<Integer, Comment> getComments() {
        return comments;
    }
    
    public static HashMap<Integer, Post> getPosts() {
        return posts;
    }
    
}
