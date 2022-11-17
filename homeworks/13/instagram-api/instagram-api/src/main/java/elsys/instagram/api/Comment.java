package elsys.instagram.api;

public class Comment {
    private static int idCounter = 0;
    private String msg;    
    private int commentId;
    private User user;
    
    Comment(User user, String comment) {
        this.user = user;
        this.msg = comment;
        this.commentId = idCounter++;
    }
    
    public String getComment() {
        return msg;
    }
    
    public int getCommentId() {
        return commentId;
    }
    
    public User getUser() {
        return user;
    }
    
    public void changeComment(User user, String newMsg) {
        if (this.user == user) {
            this.msg = newMsg;
        }
    }
    
    public static int getIdCounter() {
        return idCounter;
    }
}
