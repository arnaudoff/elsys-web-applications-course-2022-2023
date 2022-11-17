package com.example.instagram_api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Post {
    private static int id = 0;
    private String picture;
    private String text;
    private Account author;
    private Map<Integer, Comment> comments; //id
    private int commentId = 0;
    private Set<Account> likes;
    private static Map<Integer, Post> posts = new HashMap<>();

    public Post(String picture, String text, Account author) {
        id ++;
        this.picture = picture;
        this.text = text;
        this.author = author;

        comments = new HashMap<>();
        likes = new HashSet();

        posts.put(id, this);
    }

    public static int getId() {
        return id;
    }

    public Account getAuthor() {
        return author;
    }

    public int getLikes() {
        return likes.size();
    }

    public void addComment(Comment comment){
        commentId ++;
        comments.put(commentId, comment);
    }

    public Map<Integer, Comment> getComments() {
        return comments;
    }

    public void addLikeFrom(Account account){
        likes.add(account);
    }

    public static Post findPost(int postId) throws Exception {
        if (posts.containsKey(postId)) {
            return posts.get(postId);
        }
        throw new Exception("Id does not exist");
    }

    public void deleteComment(int commentId) throws Exception {
        if (comments.containsKey(commentId)){
            comments.remove(commentId);
        }
        else{
            throw new Exception("Comment id does not exist");
        }
    }
    @Override
    public String toString() {
        return "Post{" +
                "picture='" + picture + '\'' +
                ", text='" + text + '\'' +
                ", comments=" + comments +
                ", likes=" + likes +
                '}';
    }
}
