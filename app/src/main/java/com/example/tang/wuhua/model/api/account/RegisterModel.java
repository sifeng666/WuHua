package com.example.tang.wuhua.model.api.account;

/**
 * 用户注册请求的实体类
 * @author z1ycheng
 */

public class RegisterModel {
    // 用户账号
    private String account;

    // 用户名
    private String name;

    // 密码
    private String password;

    // 推送id
    private String pushId;

    /**
     * 构造方法
     */

    public RegisterModel(String account, String name, String password, String pushId) {
        this.account = account;
        this.name = name;
        this.password = password;
        this.pushId = pushId;
    }

    public RegisterModel(String account, String name, String password) {
        this(account, name, password, null);
    }

    /**
     * getter and setter
     */
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
