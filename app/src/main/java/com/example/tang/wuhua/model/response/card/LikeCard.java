package com.example.tang.wuhua.model.response.card;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * 包含点赞的必要信息
 * @author z1ycheng
 */

public class LikeCard {

    // 该条点赞记录所属的moment的id
    @SerializedName("Mid")
    private String momentId;

    // 点赞人的id
    @SerializedName("Uid")
    private String userId;

    // 点赞人的id
    @SerializedName("Nickname")
    private String nickname;

    // 点赞的时间
    @SerializedName("Time_l")
    private Date likeTime;


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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
                "momentId='" + momentId + '\'' +
                ", userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", likeTime=" + likeTime +
                '}';
    }
}
