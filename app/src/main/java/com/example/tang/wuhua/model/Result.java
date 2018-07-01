package com.example.tang.wuhua.model;

import java.util.Date;

/**
 * retrofit发送请求后得到的返回，返回model，根据需求有其他泛型
 * @author z1ycheng
 */

public class Result<T> {
    /**
     * 状态信息
     */
    public static final int SUCCEED = 1;

    public static final int ERROR_UNKNOWN = 0;

    public static final int ERROR_NOT_FOUND_USER = 4041;
    public static final int ERROR_NOT_FOUND_GROUP = 4042;
    public static final int ERROR_NOT_FOUND_GROUP_MEMBER = 4043;

    public static final int ERROR_CREATE_USER = 3001;
    public static final int ERROR_CREATE_GROUP = 3002;
    public static final int ERROR_CREATE_MESSAGE = 3003;

    public static final int ERROR_PARAMETERS = 4001;
    public static final int ERROR_PARAMETERS_EXIST_ACCOUNT = 4002;
    public static final int ERROR_PARAMETERS_EXIST_NAME = 4003;

    public static final int ERROR_SERVICE = 5001;

    public static final int ERROR_ACCOUNT_TOKEN = 2001;
    public static final int ERROR_ACCOUNT_LOGIN = 2002;
    public static final int ERROR_ACCOUNT_REGISTER = 2003;

    public static final int ERROR_ACCOUNT_NO_PERMISSION = 2010;



    // 状态码
    private int code;

    // 消息
    private String message;

    // 时间
    private Date time;

    // 数据
    private T data;


    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message=" + message +
                ", time=" + time +
                ", data=" + data +
                "}";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}