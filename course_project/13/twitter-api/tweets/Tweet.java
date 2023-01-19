package org.elsysbg.twitterapi.tweets;

import java.util.Date;

public class Tweet {
    private long id;
    private long authorId;
    private String text;
    private Date date;
    
    public Tweet(long id, long authorId, String text, Date date) {
        this.id = id;
        this.authorId = authorId;
        this.text = text;
        this.date = date;
    }
    
    public long getAuthorId() {
        return authorId;
    }
    
    public Date getDate() {
        return date;
    }
    
    public long getId() {
        return id;
    }
    
    public String getText() {
        return text;
    }
}
