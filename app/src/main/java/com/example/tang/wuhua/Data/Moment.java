package com.example.tang.wuhua.Data;

import com.example.tang.wuhua.model.response.card.MomentCard;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 18-6-30.
 */

public class Moment {
    private String id;
    private String userId;
    private User owner;
    private String content;
    private String video;
    private String location;
    private String image0;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;
    private String image7;

    private Date publishTime;
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

    public Moment(MomentCard momentCard) {
        this.userId = momentCard.getPublisherId();
        this.id = momentCard.getMomentId();
        this.content = momentCard.getText();
        this.location = momentCard.getLocation();
        this.publishTime = momentCard.getPublishTime();
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
