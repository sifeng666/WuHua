package com.example.tang.wuhua.net;

import com.example.tang.wuhua.Constant;
import com.example.tang.wuhua.net.service.BaiduApiService;
import com.example.tang.wuhua.net.service.RemoteService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
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

    // 百度retrofit对象
    private Retrofit mBaiduRetrofit;

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
    private static OkHttpClient getOkHttpClient() {

        // 如果未被实例化
        if (instance.mClient == null) {
            instance.mClient = new OkHttpClient.Builder()
                /*
                .addInterceptor(new Interceptor() { // 添加一个拦截器 用途大概是拦截每次的请求 往里面添加一点东西
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
        }

        return instance.mClient;
    }

    /**
     * 获得一个Gson对象
     * @return 一个Gson对象
     */
    private static Gson getGson() {

        // 如果未被实例化
        if (instance.mGson == null) {
            instance.mGson =
                    new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
        }

        return instance.mGson;
    }

    /**
     * 获得一个retrofit对象
     * @return retrofit对象
     */
    private static Retrofit getRetrofit() {

        // 如果retrofit对象未被实例化
        if (instance.mRetrofit == null) {
            // 确保OkHttpClient对象已经被构造
            getOkHttpClient();

            // 如果没有被构造，构造一个Retrofit对象 测试一下
            Retrofit.Builder builder = new Retrofit.Builder();
            instance.mRetrofit = builder.baseUrl(Constant.Value.BASE_URL)
                    // 设置标准数据类型的解析器 为了使传出的字符串不带引号 需要在gson解析器之前
                    // .addConverterFactory(ScalarsConverterFactory.create())
                    // 设置Gson解析器
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .client(instance.mClient)
                    .build();
        }

        return instance.mRetrofit;
    }

    /**
     * 获得baidu retrofit对象
     * @return retrofit对象
     */
    private static Retrofit getBaiduRetrofit() {

        // 如果retrofit对象未被实例化
        if (instance.mBaiduRetrofit == null) {
            // 确保OkHttpClient对象已经被构造
            getOkHttpClient();

            // 如果没有被构造，构造一个Retrofit对象 测试一下
            Retrofit.Builder builder = new Retrofit.Builder();
            instance.mBaiduRetrofit = builder.baseUrl(Constant.Value.BAIDU_API_URL)
                    // 设置标准数据类型的解析器 为了使传出的字符串不带引号 需要在gson解析器之前
                    // .addConverterFactory(ScalarsConverterFactory.create())
                    // 设置Gson解析器
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .client(instance.mClient)
                    .build();
        }

        return instance.mBaiduRetrofit;
    }

    /**
     * 返回一个请求代理
     * @return RemoteService代理
     */
    public static RemoteService remote() {
        return Network.getRetrofit().create(RemoteService.class);
    }

    /**
     * 返回百度api的请求代理
     * @return BaiduApiService服务
     */
    public static BaiduApiService baiduRemote() {
        return Network.getBaiduRetrofit().create(BaiduApiService.class);
    }
}
