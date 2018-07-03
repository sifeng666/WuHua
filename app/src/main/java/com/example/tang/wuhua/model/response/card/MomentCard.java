package com.example.tang.wuhua.model.response.card;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * 包含一个moment的必要信息
 * @author z1ycheng
 */

public class MomentCard {

    // 朋友圈id
    @SerializedName("Mid")
    private String momentId;

    // 发布人的id
    @SerializedName("Uid")
    private String publisherId;

    // 纬度
    @SerializedName("LocY")
    private double latitude;

    // 经度
    @SerializedName("LocX")
    private double longitude;

    // 文本
    @SerializedName("Text_m")
    private String text;

    // 图片
    @SerializedName("Image")
    private List<String> imagesUris;

    // 视频
    @SerializedName("Video")
    private List<String> videosUris;

    // 发布时间
    @SerializedName("Time_m")
    private Date publishTime;

    // 发布地点
    @SerializedName("Loc_Des")
    private String location;

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getImagesUris() {
        return imagesUris;
    }

    public void setImagesUris(List<String> imagesUris) {
        this.imagesUris = imagesUris;
    }

    public List<String> getVideosUris() {
        return videosUris;
    }

    public void setVideosUris(List<String> videosUris) {
        this.videosUris = videosUris;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "MomentCard{" +
                "momentId='" + momentId + '\'' +
                ", publisherId='" + publisherId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", text='" + text + '\'' +
                ", imagesUris=" + imagesUris +
                ", videosUris=" + videosUris +
                ", publishTime=" + publishTime +
                ", location='" + location + '\'' +
                '}';
    }
}
