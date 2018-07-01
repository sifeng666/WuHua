package com.example.tang.wuhua.model.api.account;

import android.util.Log;

/**
 * 登录请求的model参数
 * @author z1ycheng
 */

public class LoginModel {
    // 用户名
    private String username;

    // 密码
    private String password;

    // 推送id
    private String pushId;

    /**
     * 构造方法
     */
    public LoginModel(String username, String password){
        this(username, password, null);
    }

    public LoginModel(String username, String password, String pushId){
        this.username = username;
        this.password = password;
        this.pushId = pushId;
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

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
