package com.example.instagramhw.models;

import java.util.ArrayList;

public class Post {
    private final int id;
    private static int count = 0;
    private String text;
    private String picture;

    private static ArrayList<Post> posts = new ArrayList<>();

    private int accountId;
    private ArrayList<User> likedUsers = new ArrayList<>(); // samo username shte se pokazva
    private ArrayList<Comment> comments = new ArrayList<>();

    public static ArrayList<Post> getPosts() {
        return posts;
    }

    public static void addPost(Post post){
        posts.add(post);
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public ArrayList<User> getLikedUsers() {
        return likedUsers;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void Like(User user){
        likedUsers.add(user);
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void removeComment(Comment comment){
        comments.remove(comment);
    }

    public Post(String text, String picture, int accountId) {
        this.text = text;
        this.picture = picture;
        this.accountId = accountId;
        id = count;
        count++;
    }

    public String print(){
        StringBuilder stringToReturn = new StringBuilder("Description: " + text + "\n" + "Picture: " + picture + "\n" + "Likes: " + likedUsers.size() + "\n" + "People who like this post: ");
        for (User user : likedUsers){
            stringToReturn.append(user.getUsername()).append(", ");
        }
        stringToReturn.append("\n");
        stringToReturn.append("Comments: ");
        stringToReturn.append("\n");
        for (Comment comment : comments){
            for(User user : User.getUsers()){
                if(user.getAccountId() == comment.getUserId()){
                    stringToReturn.append("User who comment: ").append(user.getUsername()).append(" | ").append("Comment: ").append(comment.getText()).append("\n");
                }
            }
        }
        return String.valueOf(stringToReturn);
    }
}
