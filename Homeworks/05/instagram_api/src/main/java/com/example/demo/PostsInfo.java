package com.example.demo;

public class PostsInfo {
    private int id;
    private String link;
    private String description;
    private int user_id;

    public PostsInfo(int id, String link, String description, int user_id) {
        this.id = id;
        this.link = link;
        this.description = description;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public int getUser_id() {
        return user_id;
    }
}
