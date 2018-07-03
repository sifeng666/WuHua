package com.example.tang.wuhua.model.parameter;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * 点赞的model
 * @author z1ycheng
 */

public class LikeModel {

    // 该条点赞记录所属的moment的id
    @SerializedName("Mid")
    private String momentId;

    // 点赞人的id
    @SerializedName("Uid")
    private String userId;

    // 点赞的时间
    @SerializedName("Time_l")
    private Date likeTime;

    public LikeModel(String momentId, String userId) {
        this.momentId = momentId;
        this.userId = userId;
    }

    public LikeModel(String momentId, String userId, Date likeTime) {
        this.momentId = momentId;
        this.userId = userId;
        this.likeTime = likeTime;
    }

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }
}
