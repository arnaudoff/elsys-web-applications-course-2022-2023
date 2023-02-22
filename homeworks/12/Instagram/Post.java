package org.elsys.hwservlet;

import java.util.HashMap;
import java.util.Objects;

public class Post {
    private String picture;
    private String description;

    private int id;
    private static int postId = 0;

    private static HashMap<Post, User> postUserHashMap = new HashMap<>();
    private HashMap<String, User> commentUserHashMap = new HashMap<>();
    public static HashMap<Post, User> getPostUserHashMap() {
        return postUserHashMap;
    }

    public void addComment(String comment, User user){
        commentUserHashMap.put(comment,user);
    }

    public static void addPost(Post post, User user){
        postUserHashMap.put(post,user);
    }

    public static void setPostUserHashMap(HashMap<Post, User> postUserHashMap) {
        Post.postUserHashMap = postUserHashMap;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<String, User> getCommentUserHashMap() {
        return commentUserHashMap;
    }

    public void setCommentUserHashMap(HashMap<String, User> commentUserHashMap) {
        this.commentUserHashMap = commentUserHashMap;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post(String picture, String description) {
        this.picture = picture;
        this.description = description;
        id = postId++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(picture, post.picture) && Objects.equals(description, post.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(picture, description);
    }

    @Override
    public String toString() {
        return "Post{" +
                "picture='" + picture + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
