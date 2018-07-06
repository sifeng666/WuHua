package com.example.tang.wuhua.Data;

import com.example.tang.wuhua.model.response.card.MomentCard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private List<String> images;

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
        this.images = momentCard.getImagesUrisList();
        this.isLike = false;
        this.comments = new ArrayList<>();
        this.likes = new ArrayList<>();
        this.owner = new User("Ben");
        this.location = momentCard.getLocation();
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

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getLocation() {
        return location;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public List<String> getImages() {
        return images;
    }
}
