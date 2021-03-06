package com.example.tang.wuhua.model.parameter;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Ferris tale on 2018/7/5.
 */

public class RefreshModel {
    // 纬度
    @SerializedName("LocY")
    private double latitude;

    // 经度
    @SerializedName("LocX")
    private double longitude;

    // 最后一条朋友圈发布的时间
    @SerializedName("Time_m")
    private Date lastMomentTime;

    public RefreshModel(double latitude, double longitude, Date lastMomentTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastMomentTime = lastMomentTime;
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

    public Date getLastMomentTime() {
        return lastMomentTime;
    }

    public void setLastMomentTime(Date lastMomentTime) {
        this.lastMomentTime = lastMomentTime;
    }
}
