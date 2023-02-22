package com.example.instagramapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Post {
    private String image;
    private String description;
    private String author;
    private HashSet<String> likes;
    private int commentId;
    private HashMap<Integer, Comment> comments;
    private static Integer generator = 0;
    private static HashMap<Integer, Post> posts = new HashMap<>();

    public Post(String image, String description, String author) {
        this.image = image;
        this.description = description;
        this.author = author;
        this.likes = new HashSet<>();
        this.comments = new HashMap<>();
        posts.put(generator, this);
        generator++;
        this.commentId = 0;
    }

    public String getImage() {
        return image;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public int getLikes() {
        return likes.size();
    }

    public HashMap<Integer, Comment> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Post{\n" +
                "image:'" + image + '\'' +
                "\ndescription:'" + description + '\'' +
                "\nlikes:" + likes +
                "\ncomments:" + comments +
                "\n}";
    }

    public static Post getPost(int id){
        if(id < 0){
            throw new IllegalArgumentException("Id can't be smaller than 0!");
        }
        if(id >= generator){
            throw new IllegalArgumentException("There is no post with such id!");
        }
        if(!posts.containsKey(id)){
            throw new IllegalArgumentException("There is no post with such id!");
        }
        return posts.get(id);
    }

    public Comment getComment(int id){
        if(!comments.containsKey(id)){
            throw new IllegalArgumentException("There is no post with such id!");
        }
        return comments.get(id);
    }

    public void addComment(Comment newComment){
        comments.put(commentId, newComment);
        commentId++;
    }

    public void like(String userName){
        likes.add(userName);
    }

    public void deleteComment(int id){
        comments.remove(id);
    }

}
