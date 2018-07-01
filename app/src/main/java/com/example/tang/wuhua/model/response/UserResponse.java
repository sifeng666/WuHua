package com.example.tang.wuhua.model.response;

import com.example.tang.wuhua.model.response.card.UserCard;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author z1ycheng
 */

public class UserResponse {

    // 状态码
    private int state;

    private UserCard userInfo;

    private boolean success(){
        return state == 1;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public UserCard getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserCard userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "state=" + state +
                ", userInfo=" + userInfo +
                '}';
    }
}
