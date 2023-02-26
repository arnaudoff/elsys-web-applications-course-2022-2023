import java.util.HashMap;
import java.util.HashSet;

public class Post {

    private final String photo;
    private final String description;
    private final HashSet<String> like = new HashSet<>();
    private final HashMap<Integer, Comment> comments = new HashMap<>();
    private int commentId;
    private final String profile;
    private static int idGen = 0;
    private static final HashMap<Integer, Post> posts = new HashMap<>();

    public Post(String photo, String description, String profile) {
        this.photo = photo;
        this.description = description;
        this.profile = profile;
        commentId = 0;
        int id = idGen;
        posts.put(idGen++, this);
    }

    public static Post getPost(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative!");
        }
        if (id >= idGen || !posts.containsKey(id)) {
            throw new IllegalArgumentException("No post with such id!");
        }
        return posts.get(id);
    }

    public void addComment(Comment comment) {
        comments.put(commentId++, comment);
    }

    public void deleteComment(int id) {
        if (!comments.containsKey(id)) {
            throw new IllegalArgumentException("No comment with such id!");
        }
        comments.remove(id);
    }

    public void like(String user) {
        like.add(user);
    }

    public HashMap<Integer, Comment> getComments() {
        return comments;
    }

    public int getLike() {
        return like.size();
    }

    public String getProfile() {
        return profile;
    }

    @Override
    public String toString() {
        return "Post {" +
                "\n\tphoto='" + photo + '\'' +
                ", \n\tdescription='" + description + '\'' +
                ", \n\tlike=" + like +
                ", \n\tcomments=" + comments +
                "\n\t}";
    }
}
