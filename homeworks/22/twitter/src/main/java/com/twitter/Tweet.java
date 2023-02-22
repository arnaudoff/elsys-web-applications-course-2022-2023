package com.twitter;

import java.util.Date;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "tweets")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name="text", nullable = false)
    private String text;

    @CreationTimestamp
    @Column(name="created_at", nullable = false)
    private Date createdAt;

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}