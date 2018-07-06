package com.example.tang.wuhua.Data;

import com.example.tang.wuhua.model.response.UserResponse;
import com.example.tang.wuhua.model.response.card.UserCard;

import java.io.Serializable;

/**
 * Created by root on 18-6-28.
 */

public class User implements Serializable {
    private String username; //手机号
    private String id;
    private String nickname; //用户名
    private String portrait;
    private String signature;

    public User(String username) {
        this.username = username;
        this.nickname = "";
        this.signature = "";
    }

    public User(UserResponse userResponse) {
        this.username = userResponse.getUserInfo().getUsername();
        this.id = userResponse.getUserInfo().getUserId();
        this.nickname = userResponse.getUserInfo().getNickname();
        this.portrait = userResponse.getUserInfo().getPortrait();
        this.signature = userResponse.getUserInfo().getSignature();
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPortrait() {
        if (portrait == "") {
            return null;
        }
        return portrait;
    }

    public String getSignature() {
        return signature;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


}
