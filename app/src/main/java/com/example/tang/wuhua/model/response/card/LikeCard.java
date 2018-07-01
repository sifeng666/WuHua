package com.example.tang.wuhua.model.response.card;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * 包含点赞的必要信息
 * @author z1ycheng
 */

public class LikeCard {

    // 该条点赞记录的唯一标识
    @SerializedName("Lid")
    private String likeId;

    // 该条点赞记录所属的moment的id
    @SerializedName("Mid")
    private String momentId;

    // 点赞人的id
    @SerializedName("Uid")
    private String userId;

    // 点赞的时间
    @SerializedName("Time_l")
    private Date likeTime;

    public String getLikeId() {
        return likeId;
    }

    public void setLikeId(String likeId) {
        this.likeId = likeId;
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

    @Override
    public String toString() {
        return "LikeCard{" +
                "likeId='" + likeId + '\'' +
                ", momentId='" + momentId + '\'' +
                ", userId='" + userId + '\'' +
                ", likeTime=" + likeTime +
                '}';
    }
}
