package com.example.tang.wuhua.model.api.account;

import android.util.Log;

/**
 * 登录请求的model参数
 * @author z1ycheng
 */

public class LoginModel {
    // 账户
    private String account;

    // 密码
    private String password;

    // 推送id
    private String pushId;

    /**
     * 构造方法
     */
    public LoginModel(String account, String password){
        this.account = account;
        this.password = password;
    }

    public LoginModel(String account, String password, String pushId){
        this.account = account;
        this.password = password;
        this.pushId = pushId;
    }

    /**
     * getter 和 setter
     */
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
