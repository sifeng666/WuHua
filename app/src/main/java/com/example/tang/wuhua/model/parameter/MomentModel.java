package com.example.tang.wuhua.model.parameter;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * 发送朋友圈的参数model
 * @author z1ycheng
 */

public class MomentModel {

    public static int MEDIA_TYPE_IMG = 0;
    public static int MEDIA_TYPE_VIDEO = 1;
    public static int MEDIA_TYPE_NONE = 2;

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

    // 媒体文件的路径
    private List<String> mediaFilesPaths;

    // 媒体文件的类型
    @SerializedName("type")
    private int mediaFileType;

    // 所有图片路径
    //@SerializedName("Image")
   // private List<String> imagesPaths;

    // 视频的路径
    //@SerializedName("Video")
   // private String videoPath;

    // 发布时间
    @SerializedName("Time_m")
    private Date publishTime;

    // 发布地点
    @SerializedName("Loc_Des")
    private String location;


    public MomentModel(String publisherId, double latitude, double longitude,
                       String text, List<String> mediaFilesPaths, int mediaFileType,
                       Date publishTime, String location) {

        this.publisherId = publisherId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.text = text;
        this.mediaFilesPaths = mediaFilesPaths;
        this.mediaFileType = mediaFileType;
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

    public List<String> getMediaFilesPaths() {
        return mediaFilesPaths;
    }

    public void setMediaFilesPaths(List<String> mediaFilesPaths) {
        this.mediaFilesPaths = mediaFilesPaths;
    }

    public int getMediaFileType() {
        return mediaFileType;
    }

    public void setMediaFileType(int mediaFileType) {
        this.mediaFileType = mediaFileType;
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
