package org.elsys_bg.spring_hw.user;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class User {

	private @Id @GeneratedValue int id;
	private String name;
	private LocalDateTime registrationDateTime;
	private int followers;
	private int following;

	public User() {}

	public User(String name) {
		this.name = name;
		this.registrationDateTime = LocalDateTime.now();
		this.followers = 0;
		this.following = 0;
	}

	public int getId() { return id; }
	public String getName() { return name; }
	public LocalDateTime getRegistrationDateTime() { return registrationDateTime; }
	public int getFollowers() { return followers; }
	public int getFollowing() { return following; }
}
