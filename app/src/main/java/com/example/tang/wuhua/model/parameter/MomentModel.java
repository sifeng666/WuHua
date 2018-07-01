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

    // 图片
    @SerializedName("Image")
    private String image;

    // 视频
    @SerializedName("Video")
    private String video;

    // 发布时间
    @SerializedName("Time_m")
    private Date publishTime;

    // 发布地点
    @SerializedName("Loc_Des")
    private String location;

    public MomentModel(String publisherId, double latitude, double longitude,
                       String text, String image, String video,
                       Date publishTime, String location) {

        this.publisherId = publisherId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.text = text;
        this.image = image;
        this.video = video;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
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
