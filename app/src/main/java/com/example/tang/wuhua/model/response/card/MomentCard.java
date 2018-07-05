package com.example.tang.wuhua.model.response.card;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
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

    // 图片一家
    @SerializedName("Image1")
    private String imageUri1;

    @SerializedName("Image2")
    private String imageUri2;

    @SerializedName("Image3")
    private String imageUri3;

    @SerializedName("Image4")
    private String imageUri4;

    @SerializedName("Image5")
    private String imageUri5;

    @SerializedName("Image6")
    private String imageUri6;

    @SerializedName("Image7")
    private String imageUri7;

    @SerializedName("Image8")
    private String imageUri8;

    @SerializedName("Image9")
    private String imageUri9;

    // 视频
    @SerializedName("Video")
    private String videoUri;

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

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
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

    public void setImagesUrisList(List<String> imagesUris) {
        if (imagesUris != null && imagesUris.size() > 0 && imagesUris.size() <= 9) {
            imageUri1 = imagesUris.get(0);
            imageUri2 = imagesUris.get(1);
            imageUri3 = imagesUris.get(2);
            imageUri4 = imagesUris.get(3);
            imageUri5 = imagesUris.get(4);
            imageUri6 = imagesUris.get(5);
            imageUri7 = imagesUris.get(6);
            imageUri8 = imagesUris.get(7);
            imageUri9 = imagesUris.get(8);
        }
    }

    public List<String> getImagesUrisList() {
        List<String> list = new ArrayList<>();
        list.add(imageUri1);
        list.add(imageUri2);
        list.add(imageUri3);
        list.add(imageUri4);
        list.add(imageUri5);
        list.add(imageUri6);
        list.add(imageUri7);
        list.add(imageUri8);
        list.add(imageUri9);
        return list;
    }

    @Override
    public String toString() {
        return "MomentCard{" +
                "momentId='" + momentId + '\'' +
                ", publisherId='" + publisherId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", text='" + text + '\'' +
                ", imageUri1='" + imageUri1 + '\'' +
                ", imageUri2='" + imageUri2 + '\'' +
                ", imageUri3='" + imageUri3 + '\'' +
                ", imageUri4='" + imageUri4 + '\'' +
                ", imageUri5='" + imageUri5 + '\'' +
                ", imageUri6='" + imageUri6 + '\'' +
                ", imageUri7='" + imageUri7 + '\'' +
                ", imageUri8='" + imageUri8 + '\'' +
                ", imageUri9='" + imageUri9 + '\'' +
                ", videoUri='" + videoUri + '\'' +
                ", publishTime=" + publishTime +
                ", location='" + location + '\'' +
                '}';
    }
}
