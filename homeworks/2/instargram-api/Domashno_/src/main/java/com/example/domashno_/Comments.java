package com.example.domashno_;

import java.util.ArrayList;

public class Comments {
    static ArrayList<Comments> comments = new ArrayList<>();
    String comment;
    String PostID;
    String UserID;

    String CommentID;

    public Comments(int CommentID, String PostID, String UserID, String comment) {
        this.PostID = PostID;
        this.UserID = UserID;
        this.comment = comment;
        this.CommentID = setId(CommentID);
    }
    public Comments(){}

    public String setId(int CommentID) {
        this.CommentID =Integer.toString(CommentID);
        return this.CommentID;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentID() {
        return CommentID;
    }

    public String getComment() {
        return comment;
    }

    public String getPostID() {
        return PostID;
    }

    public String getUserID() {
        return UserID;
    }
}