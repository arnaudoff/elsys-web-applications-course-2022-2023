package org.elsys_bg.spring_hw.tweet;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class Tweet {

	private @Id @GeneratedValue int id;
	private int authorId;
	private String text;
	private LocalDateTime date;

	public Tweet() {}

	public Tweet(int authorId, String text) {
		this.authorId = authorId;
		this.text = text;
		this.date = LocalDateTime.now();
	}

	public int getId() { return id; }
	public int getAuthorId() { return authorId; }
	public String getText() { return text; }
	public LocalDateTime getDate() { return date; }

	public void updateTweet(String text) {
		this.text = text;
	}
}
