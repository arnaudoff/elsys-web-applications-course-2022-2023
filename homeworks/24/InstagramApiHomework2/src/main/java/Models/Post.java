package Models;

public class Post {
    private int id;
    private String description;
    private String poster_username;
    private int likes;

    public Post(String description) {
        this.description = description;
    }
    public Post(int id, String description, String poster_username, int likes) {
        this.id = id;
        this.description = description;
        this.poster_username = poster_username;
        this.likes = likes;
    }



    public int getId() {
        return id;
    }

    public String getPoster_username() {
        return poster_username;
    }

    public int getLikes() {
        return likes;
    }

    public String getDescription() {
        return description;
    }
    @Override
    public String toString() {
        return "Post{" +
                "description='" + description + '\'' +
                '}';
    }
}
