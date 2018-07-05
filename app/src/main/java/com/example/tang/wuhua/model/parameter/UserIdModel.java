package com.example.tang.wuhua.model.parameter;

import com.google.gson.annotations.SerializedName;

/**
 * 用户id的model
 * @author z1ycheng
 */

public class UserIdModel {
    @SerializedName("Uid")
    private String userId;

    public UserIdModel(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
