package org.elsys_bg.spring_hw.tweet;


public class PostTweetRequest {
	
	private int authorId;
	private String text;

	public PostTweetRequest() {}

	public int getAuthorId() { return authorId; }
	public String getText() { return text; }
}
