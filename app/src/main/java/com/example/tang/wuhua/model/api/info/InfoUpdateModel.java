package com.example.tang.wuhua.model.api.info;

/**
 * @author z1ycheng
 */

public class InfoUpdateModel {

    private String nickname;

    private String portrait;

    private String description;

    private int gender;

    public InfoUpdateModel(String nickname, String portrait, String description, int gender) {
        this.nickname = nickname;
        this.portrait = portrait;
        this.description = description;
        this.gender = gender;
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
}
