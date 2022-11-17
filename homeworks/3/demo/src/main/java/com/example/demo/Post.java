package com.example.demo;

public class Post {
    private String pic_url;
    private String Description;

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Post(String pic_url, String description) {
        this.pic_url = pic_url;
        Description = description;
    }
}
