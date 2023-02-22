package com.example.instagramapi;

public class Comment {
    private String text;
    private String author;

    public Comment(String text, String author) {
        this.text = text;
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment {" +
                "\n\tcomm-text:'" + text + '\'' +
                ", \n\tauthor:'" + author + '\'' +
                "\n\t}";
    }
}
