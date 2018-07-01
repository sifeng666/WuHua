package com.example.tang.wuhua.model.parameter;

import com.google.gson.annotations.SerializedName;

/**
 * 用户修改个人信息的参数model
 * @author z1ycheng
 */

public class InfoUpdateModel {
    // 用户id
    @SerializedName("Uid")
    private String userId;

    // 昵称
    @SerializedName("Nickname")
    private String nickname;

    // 头像
    @SerializedName("Profile_pic")
    private String portrait;

    // 个人介绍
    @SerializedName("Sign")
    private String signature;

    public InfoUpdateModel(String userId, String nickname, String portrait, String signature) {
        this.userId = userId;
        this.nickname = nickname;
        this.portrait = portrait;
        this.signature = signature;
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
}
