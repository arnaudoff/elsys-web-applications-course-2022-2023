package org.elsys.courseproject;

import java.util.Date;
import java.util.UUID;

public class Tweet {
    private String Id;

    private String authorId;

    private String text;

    private Date date;

    public String getId() {
        return Id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Tweet(String authorId, String text) {
        this.authorId = authorId;
        this.text = text;
        Id = UUID.randomUUID().toString();
        date = new Date();
    }
}
