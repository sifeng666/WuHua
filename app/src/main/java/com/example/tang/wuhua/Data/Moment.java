package com.example.tang.wuhua.Data;

import java.util.ArrayList;

/**
 * Created by root on 18-6-30.
 */

public class Moment {
    private String id;
    private User owner;
    private String content;
    private boolean isLike;

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    private ArrayList<Comment> comments;
    private ArrayList<String> likes;

    public Moment(User owner, String content) {
        this.owner = owner;
        this.content = content;
        this.comments = new ArrayList<>();
        this.likes = new ArrayList<>();
        this.isLike = false;
//        this.likes.add("库里");
//        this.likes.add("科比");
//        this.likes.add("奥尼尔");
    }

    public User getOwner() {
        return owner;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }
}
