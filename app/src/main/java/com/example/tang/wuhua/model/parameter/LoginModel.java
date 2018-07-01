package com.example.tang.wuhua.model.parameter;

import com.google.gson.annotations.SerializedName;

/**
 * 登录方法的model参数
 * @author z1ycheng
 */

public class LoginModel {

    // 用户名
    @SerializedName("UserName")
    private String username;

    // 密码
    @SerializedName("PassWord")
    private String password;

    /**
     * 构造方法
     */
    public LoginModel(String username, String password){
        this.username = username;
        this.password = password;
    }

    /**
     * getter 和 setter
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
