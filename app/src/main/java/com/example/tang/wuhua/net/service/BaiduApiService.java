package com.example.tang.wuhua.net.service;

import com.example.tang.wuhua.model.response.GeocoderResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 百度api的接口
 * @author z1ycheng
 */

public interface BaiduApiService {

    /**
     * 请求解码
     * @param location 经纬度
     * @param ak 百度api的ak 在Constant里面
     * @param mcode 百度api的mcode 在Constant里面
     * @return 返回体
     */
    @GET("geocoder/v2/")
    Call<ResponseBody> locationToAddress(@Query("location") String location,
                                         @Query("ak") String ak,
                                         @Query("mcode") String mcode);
}
