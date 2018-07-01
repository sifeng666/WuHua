package com.example.tang.wuhua.model.response.card;

import com.google.gson.annotations.SerializedName;

/**
 * 包含用户对象的必要信息
 * @author z1ycheng
 */

public class UserCard {
    // 用户id
    @SerializedName("Uid")
    private String userId;

    // 用户名
    @SerializedName("UserName")
    private String username;

    // 昵称
    @SerializedName("Nickname")
    private String nickname;

    // 头像
    @SerializedName("Profile_pic")
    private String portrait;

    // 个人介绍
    @SerializedName("Sign")
    private String signature;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "UserCard{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", portrait='" + portrait + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
