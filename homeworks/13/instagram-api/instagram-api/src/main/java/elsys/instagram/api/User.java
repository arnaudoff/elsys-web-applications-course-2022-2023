package elsys.instagram.api;

import java.util.HashMap;


class InvalidUsernameException extends Exception {

    InvalidUsernameException(String str) {
        super(str);
    }
}

public class User {
    private static HashMap<String, User> users = new HashMap<String, User>();

    private String firstName;
    private String lastName;
    private String username;
    private String password;

    User(String firstName, String lastName, String username, String password) throws InvalidUsernameException {

        if (isRegistered(username)) {
            throw new InvalidUsernameException("Username is taken");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public static boolean isRegistered(String username) {
        return users.containsKey(username);
    }

    public static User getUser(String username) throws InvalidUsernameException {
        if (!isRegistered(username)) {
            throw new InvalidUsernameException("User isn't registered");
        }

        return users.get(username);
    }

    public static void registerUser(User user) throws InvalidUsernameException {
        if (isRegistered(user.getUsername())) {
            throw new InvalidUsernameException("User is registered");
        }

        users.put(user.getUsername(), user);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
