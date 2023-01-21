package com.example.instagramhw.models;

public class Comment {
    private final int id;
    private static int count = 0;
    private int postId;
    private String text;

    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Comment(int postId, String text) {
        this.postId = postId;
        this.text = text;
        id = count;
        count++;
    }
}
