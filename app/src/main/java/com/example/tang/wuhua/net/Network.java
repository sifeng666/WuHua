package com.example.tang.wuhua.net;

import android.text.TextUtils;

import com.example.tang.wuhua.Account;
import com.example.tang.wuhua.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求的封装
 * @author z1ycheng
 */

public class Network {

    // 网络请求的实例
    private static Network instance;

    // retrofit对象
    private Retrofit mRetrofit;

    // OkHttpClient对象
    private OkHttpClient mClient;

    // Gson对象
    private Gson mGson;

    static {
        instance = new Network();
    }

    // private构造函数不允许外部使用
    private Network() {

    }

    /**
     * 获得一个OkHttpClient对象
     * @return OkHttpClient对象
     */
    public static OkHttpClient getOkHttpClient() {

        // 如果对象已经被实例化，直接返回
        if (instance.mClient != null) {
            return instance.mClient;
        }
        // 否则创建一个OkHttpClient对象
        OkHttpClient client = new OkHttpClient.Builder()
                /*
                .addInterceptor(new Interceptor() { // 添加一个拦截器 作用是缓存okhttp已经实现
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        // 拦截我们发起请求的request，之后为其添加头部，最后返回添加后的对象
                        Request request = chain.request();
                        // 重新封装builder
                        Request.Builder builder = request.newBuilder();
                        if (!TextUtils.isEmpty(Account.getToken())) // 如果token不为空
                            // 注入一个Token
                            builder.addHeader("token", Account.getToken());
                        builder.addHeader("Content-Type", "application/json");//添加数据格式 不是必须的retrofit2已经帮我们完成了
                        Request newRequest = builder.build();
                        // 返回封装Header之后的Request
                        return chain.proceed(newRequest);
                    }
                })*/
                .build();
        instance.mClient = client;
        return instance.mClient;
    }

    /**
     * 获得一个Gson对象
     * @return 一个Gson对象
     */
    private static Gson getGson() {
        if (instance.mGson != null)
            return instance.mGson;
        else {
            // 先构造一个Gson对象
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd hh:mm:ss")
                    .create();
            instance.mGson = gson;
            return instance.mGson;
        }
    }

    /**
     * 获得一个retrofit对象
     * @return retrofit对象
     */
    private static Retrofit getRetrofit() {

        // 如果retrofit对象已经构造了，直接返回
        if (instance.mRetrofit != null) {
            return instance.mRetrofit;
        }

        // 确保OkHttpClient对象已经被构造
        getOkHttpClient();


        // 如果没有被构造，构造一个Retrofit对象
        Retrofit.Builder builder = new Retrofit.Builder();
        instance.mRetrofit = builder.baseUrl(Constant.Value.BASE_URL)
                //设置Gson解析器
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(instance.mClient)
                .build();

        return instance.mRetrofit;
    }

    /**
     * 返回一个请求代理
     * @return RemoteService代理
     */
    public static RemoteService remote() {
        return Network.getRetrofit().create(RemoteService.class);
    }
}
