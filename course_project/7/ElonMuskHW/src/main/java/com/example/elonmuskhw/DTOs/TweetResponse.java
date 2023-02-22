package com.example.elonmuskhw.DTOs;

import java.util.Date;

public class TweetResponse implements Comparable<TweetResponse>{
    private String text;

    private Date tweetDate;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTweetDate() {
        return tweetDate;
    }

    public void setTweetDate(Date tweetDate) {
        this.tweetDate = tweetDate;
    }

    public TweetResponse(String text, Date tweetDate) {
        this.text = text;
        this.tweetDate = tweetDate;
    }

    @Override
    public int compareTo(TweetResponse o) {
        return o.getTweetDate().compareTo(getTweetDate());
    }
}
