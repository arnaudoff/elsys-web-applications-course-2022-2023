package Models;

public class Like {
    private String username;
    private int postId;

    public Like(String username, int postId) {
        this.username = username;
        this.postId = postId;
    }

    public String getUsername() {
        return username;
    }

    public int getPostId() {
        return postId;
    }


}
