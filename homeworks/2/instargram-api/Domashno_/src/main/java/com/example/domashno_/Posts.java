package com.example.domashno_;

import java.util.ArrayList;

public class Posts {
    static ArrayList<Posts> archive = new ArrayList<>();
    String UserID;
    String picture;
    String description;

    String PostID;

    public Posts(int PostID,String userID, String picture, String description) {
        this.UserID = userID;
        this.picture = picture;
        this.description = description;
        this.PostID = setId(PostID);
    }

    public String getPostID() {
        return PostID;
    }

    public Posts(){}

    public String setId(int id) {
        this.PostID =Integer.toString(id);
        return this.PostID;
    }

    public String getUserID() {
        return UserID;
    }

    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }
}
