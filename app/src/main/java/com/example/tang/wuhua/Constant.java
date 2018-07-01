package com.example.tang.wuhua;

/**
 * @author z1ycheng
 * 一些持久化常量
 */

public interface Constant {
    interface Value {
        // 基础url
        String BASE_URL = "http://192.168.1.1:8080/";

        // 乐帮的url
        // String BASE_URL = "http://139.199.9.244:8082/";

        // 电话号码的正则
        String REGEX_PHONENUM = "[1][3,4,5,7,8][0-9]{9}$";
    }
}
