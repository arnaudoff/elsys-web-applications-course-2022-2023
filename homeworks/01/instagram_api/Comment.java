package com.example.instagram_api;

public class Comment {
    private String comment;
    private Account author;

    public Comment(String comment, Account author) {
        this.comment = comment;
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public Account getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Comment{" +
                ", comment='" + comment + '\'' +
                ", author=" + author +
                '}';
    }
}
