package com.example.domashno_;

import java.util.ArrayList;

public class Likes {
    static ArrayList<Likes> likes = new ArrayList<>();
    String PostID;
    String UserID;

    String LikeID;

    public Likes(int LikeID, String PostID, String UserID) {
        this.PostID = PostID;
        this.UserID = UserID;
        this.LikeID = setId(LikeID);

    }
    public Likes(){}

    public String setId(int LikedID) {
        this.LikeID =Integer.toString(LikedID);
        return this.LikeID;
    }

    public String getLikeID() {
        return LikeID;
    }

    public String getPostID() {
        return PostID;
    }

    public String getUserID() {
        return UserID;
    }
}