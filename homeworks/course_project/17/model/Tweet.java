package com.firmata.elon.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Tweet")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "user_id")
    private int userId;
    private String text;
    @Column(name = "posted_at")
    @CreationTimestamp
    private Timestamp postedAt;


    public Tweet(String text, int userId) {
        super();
        this.text = text;
        this.userId = userId;
    }
}
