package com.example.tang.wuhua.model.card;

import java.util.Date;

/**
 * @author z1ycheng
 */

public class UserCard {

    // 用户id
    private String userId;

    // 用户名
    private String username;

    // 昵称
    private String nickname;

    // 备注
    private String alias;

    // 头像
    private String portrait;

    // 个人介绍
    private String description;

    // 性别
    private int gender;

    // 用户信息最后更新时间
    private Date latestModifiedAt;


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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Date getLatestModifiedAt() {
        return latestModifiedAt;
    }

    public void setLatestModifiedAt(Date latestModifiedAt) {
        this.latestModifiedAt = latestModifiedAt;
    }
}
