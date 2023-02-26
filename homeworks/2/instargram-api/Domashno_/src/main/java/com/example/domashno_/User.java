package com.example.domashno_;

import java.util.ArrayList;

public class User {
    static ArrayList<User> users = new ArrayList<>();
    String name;
    String lastname;
    String email;
    String password;
    String ID;
    public User(int id, String name, String lastname, String email, String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.ID = setId(id);
    }
 public User(){}

    public String setId(int id) {
        this.ID =Integer.toString(id);
        return ID;
    }

    public String getId() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
