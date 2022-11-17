package com.instagramapi;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Post {
    private long id;
    private String postLink;
    private String description;
    private long likes = 0;

    private final HashMap<Long, String> comments = new HashMap<>();
    private static long commentId = 0;
    public void addComment(String comment){
        comments.put(commentId++, comment);
    }

    public static ArrayList<Post> getPostsByUser(Long userId){
        ArrayList<Post> postsByUser = new ArrayList<>();
        for (Map.Entry<Long, AbstractMap.SimpleEntry<Long, Post>> entry : posts.entrySet()) {
            if(entry.getValue().getKey().equals(userId)){
                postsByUser.add(entry.getValue().getValue());
            }
        }
        return postsByUser;
    }

    public void editComment(Long commentId, String newComment){
        comments.replace(commentId, newComment);
    }

    public void deleteComment(Long commentId){
        comments.remove(commentId);
    }

    public HashMap<Long, String> getComments(){
        return comments;
    }
    private static final HashMap<Long,  AbstractMap.SimpleEntry<Long, Post>> posts = new HashMap<>();
    private static final HashMap<Long, Boolean> likedBy = new HashMap<>();
    public long getId() {
        return id;
    }
    private static long idCounter = 0;

    public static void addPost(Post post, Long userId){
        posts.put(post.getId(),new AbstractMap.SimpleEntry<>(userId, post));
    }

    public static Post getPost(Long postId){
        return posts.get(postId).getValue();
    }
    public void generateId() {
        this.id = idCounter++;
    }

    public void like(Long userId){
        if(likedBy.get(userId) == null){
            likedBy.put(userId, true);
            this.likes++;
        }
        else throw new RuntimeException();
    }
}
