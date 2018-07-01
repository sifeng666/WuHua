package com.example.tang.wuhua.model.response;

/**
 * 仅包含状态的响应体
 * @author z1ycheng
 */

public class BaseResponse {

    private int state;

    // 用于判断请求是否成功
    public boolean success() {
        return state == 1;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "state=" + state +
                '}';
    }
}
