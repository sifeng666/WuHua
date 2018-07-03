package com.example.tang.wuhua.model.parameter;

import com.google.gson.annotations.SerializedName;

/**
 * 用户修改个人信息的参数model
 * @author z1ycheng
 */

public class InfoUpdateModel {
    // 用户id
    @SerializedName("UserName")
    private String username;

    // 昵称
    @SerializedName("Nickname")
    private String nickname;

    // 密码
    @SerializedName("PassWord")
    private String password;

    // 头像路径
    @SerializedName("Profile_pic")
    private String portraitPath;

    // 个人介绍
    @SerializedName("Sign")
    private String signature;

    public InfoUpdateModel(String username, String nickname, String password, String portraitPath, String signature) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.portraitPath = portraitPath;
        this.signature = signature;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPortraitPath() {
        return portraitPath;
    }

    public void setPortraitPath(String portraitPath) {
        this.portraitPath = portraitPath;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
