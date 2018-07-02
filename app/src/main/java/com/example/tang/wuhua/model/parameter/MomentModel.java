package com.example.tang.wuhua.model.parameter;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * 发送朋友圈的参数model
 * @author z1ycheng
 */

public class MomentModel {

    // 发布人的id
    @SerializedName("Uid")
    private String publisherId;

    // 纬度
    @SerializedName("LocX")
    private double latitude;

    // 经度
    @SerializedName("LocY")
    private double longitude;

    // 文本
    @SerializedName("Text_m")
    private String text;

    // 所有图片路径
    @SerializedName("Image")
    private String[] images;

    // 所有视频路径
    @SerializedName("Video")
    private String[] videos;

    // 发布时间
    @SerializedName("Time_m")
    private Date publishTime;

    // 发布地点
    @SerializedName("Loc_Des")
    private String location;

    public MomentModel(String publisherId, double latitude, double longitude,
                       String text, String[] images, String[] videos,
                       Date publishTime, String location) {

        this.publisherId = publisherId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.text = text;
        this.images = images;
        this.videos = videos;
        this.publishTime = publishTime;
        this.location = location;
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

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String[] getVideos() {
        return videos;
    }

    public void setVideos(String[] videos) {
        this.videos = videos;
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
}
