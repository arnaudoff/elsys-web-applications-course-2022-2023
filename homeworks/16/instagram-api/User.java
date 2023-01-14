import java.util.ArrayList;

public class User {
    
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private ArrayList<Post> posts;

    public User(String firstName, String lastName, String username, String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        posts = new ArrayList<>();

    }

    public void addPost(Post post) {

        posts.add(post);

    }

    public String getFirstName() {

        return firstName;

    }

    public String getLastName() {

        return lastName;

    }

    public String getUsername() {

        return username;

    }

    public String getPassword() {

        return password;

    }

    public ArrayList<Post> getPosts() {

        return posts;
        
    }

    @Override

    public String toString() {

        return "User {" +
                "\n\tfirstName=\"" + firstName + '"' +
                ", \n\tlastName=\"" + lastName + '"' +
                ", \n\tusername=\"" + username + '"' +
                ", \n\tpassword=\"" + password + '"' +
                ", \n\tposts=" + posts +
                "\n}";

    }
}
