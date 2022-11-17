package com.example.instagram;

public class CommentsJson {

    private int id;
    private String content;
    private int user_id;
    private int post_id;

    public CommentsJson(int id, String content, int user_id, int post_id) {
        this.id = id;
        this.content = content;
        this.user_id = user_id;
        this.post_id = post_id;
    }
}
