package com.example.tang.wuhua.model.parameter;

import com.google.gson.annotations.SerializedName;

/**
 * @author z1ycheng
 */

public class MomentIdModel {

    @SerializedName("Mid")
    private String momentId;

    public MomentIdModel(String momentId) {
        this.momentId = momentId;
    }

    public String getMomentId() {
        return momentId;
    }

    public void setMomentId(String momentId) {
        this.momentId = momentId;
    }
}
