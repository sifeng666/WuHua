package com.example.tang.wuhua.model.parameter;

import com.google.gson.annotations.SerializedName;

/**
 * 用户注册请求的实体类参数
 * @author z1ycheng
 */

public class RegisterModel {

    // 用户名
    @SerializedName("UserName")
    private String username;

    // 密码
    @SerializedName("PassWord")
    private String password;

    /**
     * 构造方法
     */

    public RegisterModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * getter and setter
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
