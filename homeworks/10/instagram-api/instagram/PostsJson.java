package com.example.instagram;

public class PostsJson {
    private int id;
    private String link;
    private String description;
    private int user_id;

    public PostsJson(int id, String link, String description, int user_id) {
        this.id = id;
        this.link = link;
        this.description = description;
        this.user_id = user_id;
    }
}
