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

        // 百度api的url
        String BAIDU_API_URL = "http://api.map.baidu.com/";


        // 电话号码的正则
        String REGEX_PHONENUM = "[1][3,4,5,7,8][0-9]{9}$";

        // 百度api的ak
        String BAIDU_API_AK = "VLmb30ftbg4nTLF5uTKqduTVtOxp7mCi";

        // 百度api安全码
        String BAIDU_API_MCODE = "D5:6E:00:F7:CC:57:F7:ED:93:71:14:0B:65:8C:60:B6:BC:2B:67:A6;com.example.tang.wuhua";
    }
}
