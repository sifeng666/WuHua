package com.example.tang.wuhua.model.api.account;

/**
 * 用户注册请求的实体类参数
 * @author z1ycheng
 */

public class RegisterModel {
    // 用户名
    private String username;

    // 密码
    private String password;

    // 推送id
    private String pushId;

    /**
     * 构造方法
     */

    public RegisterModel(String username, String password, String pushId) {
        this.username = username;
        this.password = password;
        this.pushId = pushId;
    }

    public RegisterModel(String username, String password) {
        this(username, password, null);
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

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
