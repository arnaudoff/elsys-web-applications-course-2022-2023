package com.example;

import java.util.HashMap;
import java.util.HashSet;

public class Post {

    private String picture;
    private String text;
    private HashSet<String> likes;
    private HashMap<Integer, Comment> comments;
    private int commentId;
    private int id;
    private String author;
    private static int idGen = 0;
    private static HashMap<Integer, Post> posts = new HashMap<>();

    public Post(String picture, String text, String author) {
        this.picture = picture;
        this.text = text;
        this.author = author;
        comments = new HashMap<>();
        likes = new HashSet<>();
        commentId = 0;
        id = idGen;
        posts.put(idGen++, this);
    }

    public static Post getPost(int id) {
        if (!posts.containsKey(id)) {
            throw new IllegalArgumentException("No post with such id!");
        }
        return posts.get(id);
    }

    public void addComment(Comment comment) {
        comments.put(commentId++, comment);
    }

    public Comment getComment(int id) {
        if (!comments.containsKey(id)) {
            throw new IllegalArgumentException("No comment with such id found!");
        }
        return comments.get(id);
    }

    public void deleteComment(int id) {
        if (!comments.containsKey(id)) {
            throw new IllegalArgumentException("No comment with such id!");
        }
        comments.remove(id);
    }

    public void like(String user) {
        likes.add(user);
    }

    public HashMap<Integer, Comment> getComments() {
        return comments;
    }

    public int getLikes() {
        return likes.size();
    }

    public String getPicture() {
        return picture;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }
}
