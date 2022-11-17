package Models;

public class Comment {
    private int id;
    private String comment;
    private String username;
    private int postId;


    public int getPostId() {
        return postId;
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }

    public Comment(int id, String comment, String username, int postId) {
        this.id = id;
        this.comment = comment;
        this.username = username;
        this.postId = postId;
    }

    public Comment(int postId, String username, String comment) {
        this.postId = postId;
        this.username = username;
        this.comment = comment;
    }
}
