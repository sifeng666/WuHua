package com.example.tang.wuhua.model.parameter;

import com.google.gson.annotations.SerializedName;


/**
 * 用户取消点赞的model
 * @author z1ycheng
 */

public class LikeCancelModel {

    @SerializedName("Uid")
    private String userId;

    @SerializedName("Mid")
    private String momentId;

    public LikeCancelModel(String userId, String momentId) {
        this.userId = userId;
        this.momentId = momentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }
}
