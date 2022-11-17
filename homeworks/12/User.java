package org.elsys.hwservlet;

import java.util.Objects;
import java.util.Vector;

public class User {
    private static int accountId = 0;
    private int id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;

    private static Vector<User> userVector = new Vector<>();

    public static int getAccountId() {
        return accountId;
    }

    public static void setAccountId(int accountId) {
        User.accountId = accountId;
    }

    public static Vector<User> getUserVector() {
        return userVector;
    }

    public static void addUser(User user){
        userVector.add(user);
    }

    public static void setUserVector(Vector<User> userVector) {
        User.userVector = userVector;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(String firstname, String lastname, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        id = accountId++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, username, password);
    }
}
