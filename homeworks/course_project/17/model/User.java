package com.firmata.elon.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String username;
    private int followers = 0;
    private int following = 0;
    @Column(name = "registered_at")
    @CreationTimestamp
    private Timestamp registeredAt;


    public User(String username) {
        super();
        this.username = username;
    }

    public User(int id, String username, Timestamp registeredAt) {
        super();
        this.id = id;
        this.username = username;
        this.registeredAt = registeredAt;
    }
}
