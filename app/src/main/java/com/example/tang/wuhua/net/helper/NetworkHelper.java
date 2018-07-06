package com.example.tang.wuhua.net.helper;

import android.util.Log;

import com.example.tang.wuhua.model.parameter.CommentModel;
import com.example.tang.wuhua.model.parameter.InfoUpdateModel;
import com.example.tang.wuhua.model.parameter.LikeCancelModel;
import com.example.tang.wuhua.model.parameter.LikeModel;
import com.example.tang.wuhua.model.parameter.LoginModel;
import com.example.tang.wuhua.model.parameter.MomentIdModel;
import com.example.tang.wuhua.model.parameter.MomentModel;
import com.example.tang.wuhua.model.parameter.RefreshModel;
import com.example.tang.wuhua.model.parameter.RegisterModel;
import com.example.tang.wuhua.model.parameter.UserIdModel;
import com.example.tang.wuhua.model.response.BaseResponse;
import com.example.tang.wuhua.model.response.CommentResponse;
import com.example.tang.wuhua.model.response.LikeResponse;
import com.example.tang.wuhua.model.response.MomentResponse;
import com.example.tang.wuhua.model.response.UserResponse;
import com.example.tang.wuhua.net.Network;
import com.example.tang.wuhua.net.service.RemoteService;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * RemoteService接口中的网络请求的封装
 * @author z1ycheng
 */

public class NetworkHelper {

    /**
     * 将字符串转换为RequestBody
     * @param value 字符串
     * @return RequestBody
     */
    private static RequestBody toRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    /**
     * 注册的封装 异步请求
     * OK
     *
     * @param registerModel 注册model
     * @param callback 回调函数
     */
    public static void register(RegisterModel registerModel, Callback<BaseResponse> callback){
        RemoteService remoteService = Network.remote();
        Call<BaseResponse> call = remoteService.register(registerModel);
        call.enqueue(callback);
    }

    /**
     * 登录的封装 异步请求
     * OK
     *
     * @param loginModel 登录model
     * @param callback 回调函数
     */
    public static void login(LoginModel loginModel,  Callback<UserResponse> callback) {
        RemoteService remoteService = Network.remote();
        Call<UserResponse> call =
                //remoteService.login(loginModel.getUsername(), loginModel.getPassword());
                remoteService.login(loginModel);
        call.enqueue(callback);
    }

    /**
     * 获取更多信息
     *
     *
     * @param latitude 当前用户的纬度信息
     * @param longitude 当前用户的经度信息
     * @param isFingerFromUpToDown 是否从上往下滑动
     * @param lastMomentTime 主页面最后一条moment的发布时间
     * @param callback 回调函数
     */
    public static void refreshMoments(double latitude, double longitude,
                                      boolean isFingerFromUpToDown, Date lastMomentTime,
                                      Callback<MomentResponse> callback ) {

        RemoteService remoteService = Network.remote();

        Date paramDate = lastMomentTime;

        // 如果是手指从上往下滑的刷新
        // 主页面获取最新的十条moment显示在主页面上
        if (isFingerFromUpToDown) {
            // 此时可以向服务器发送一个在本次请求时间之后的一个日期（如1天后）
            // 服务器在此日期之前最近的10条即是最新的10条
            // 返回一天之后的时间
            long oneDay = 24 * 60 * 60 * 1000;
            paramDate = new Date(System.currentTimeMillis() + oneDay);
        }
        else {
            paramDate = new Date(lastMomentTime.getTime() - 1000);
        }


        Call<MomentResponse> call =
                remoteService.refreshMoments(new RefreshModel(latitude, longitude, paramDate));
        call.enqueue(callback);
    }

    /**
     * 根据moment的id查找该moment下的所有评论 异步请求
     *
     *
     * @param momentId moment的id
     * @param callback 回调函数
     */
    public static void getCommentsByMomentId(String momentId, Callback<CommentResponse> callback){
        RemoteService remoteService = Network.remote();
        Call<CommentResponse> call = remoteService.getComments(new MomentIdModel(momentId));
        call.enqueue(callback);
    }

    /**
     * 根据moment的id请求该moment下的所有赞
     * OK
     *
     * @param momentId moment的id
     * @param callback 回调函数
     */
    public static void getLikesByMomentId(String momentId, Callback<LikeResponse> callback){
        RemoteService remoteService = Network.remote();
        Call<LikeResponse> call = remoteService.getLikes(new MomentIdModel(momentId));
        call.enqueue(callback);
    }

    /**
     * 发布一条朋友圈动态
     *
     *
     * @param momentModel 朋友圈动态model
     * @param callback 回调函数
     */
    public static void publishMoment(MomentModel momentModel, Callback<BaseResponse> callback) {

        // 远程服务
        RemoteService remoteService = Network.remote();

        // 媒体文件的目录
        List<String> mediaFilesPaths = momentModel.getMediaFilesPaths();

        // MultipartBody.Part的列表
        List<MultipartBody.Part> mediaParts = new ArrayList<>();

        if (momentModel.getMediaFileType() == MomentModel.MEDIA_TYPE_IMG
                || momentModel.getMediaFileType() == MomentModel.MEDIA_TYPE_VIDEO) {
            for (int i = 0; i < mediaFilesPaths.size(); ++ i) {
                try {
                    // 首先根据路径构造一个file对象
                    File mediaFile = new File(mediaFilesPaths.get(i));

                    // 创建一个RequestBody
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse("multipart/form-data"), mediaFile);

                    // 根据这个RequestBody创建一个MultipartBody.Part
                    MultipartBody.Part part = MultipartBody.Part.createFormData(
                            (momentModel.getMediaFileType() == MomentModel.MEDIA_TYPE_IMG) ?
                                    "img" + i : "video", mediaFile.getName(), requestBody);

                    mediaParts.add(part);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("Uid", toRequestBody(momentModel.getPublisherId()));
        bodyMap.put("LocY", toRequestBody(String.valueOf(momentModel.getLatitude())));
        bodyMap.put("LocX", toRequestBody(String.valueOf(momentModel.getLongitude())));
        bodyMap.put("Text_m", toRequestBody(momentModel.getText()));
        bodyMap.put("type", toRequestBody(String.valueOf(momentModel.getMediaFileType())));

        // 将日期格式转化为字符串
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String publishTimeString = simpleDateFormat.format(momentModel.getPublishTime());
        Log.d("time", publishTimeString);
        bodyMap.put("Time_m", toRequestBody(publishTimeString));

        bodyMap.put("Loc_Des", toRequestBody(String.valueOf(momentModel.getLocation())));
        Call<BaseResponse> call = remoteService.publishMoment(bodyMap, mediaParts);
        call.enqueue(callback);
    }

    /**
     * 发布一条评论
     *
     *
     * @param commentModel 评论实体
     * @param callback 回调函数
     */
    public static void publishComment(CommentModel commentModel, Callback<BaseResponse> callback){
        RemoteService remoteService = Network.remote();
        Call<BaseResponse> call = remoteService.publishComment(commentModel);
        call.enqueue(callback);
    }

    /**
     * 给某条朋友圈点赞
     * OK
     *
     * @param likeModel 点赞的likeModel
     * @param callback 回调函数
     */
    public static void like(LikeModel likeModel, Callback<BaseResponse> callback) {

        RemoteService remoteService = Network.remote();
        Date curTime = new Date(System.currentTimeMillis()); // 不用手动生成当前时间
        likeModel.setLikeTime(curTime);
        Call<BaseResponse> call = remoteService.like(likeModel);
        call.enqueue(callback);
    }

    /**
     * 给某条朋友圈取消点赞
     *
     *
     * @param userId 取消点赞人的id
     * @param momentId 取消点赞的朋友圈的id
     * @param callback 回调函数
     */
    public static void likeCancel(String userId, String momentId,
                                  Callback<BaseResponse> callback) {

        RemoteService remoteService = Network.remote();
        Call<BaseResponse> call = remoteService.likeCancel(new LikeCancelModel(userId, momentId));
        call.enqueue(callback);
    }


    /**
     * 更新用户信息
     * OK
     *
     * @param infoUpdateModel 更新用户信息的model
     * @param callback 回调函数
     */
    public static void updateUserInfo(InfoUpdateModel infoUpdateModel,
                                      Callback<BaseResponse> callback) {

        // 获取远程服务
        RemoteService remoteService = Network.remote();

        // 头像图片的本地路径
        String portraitPath = infoUpdateModel.getPortraitPath();

        // 图片的请求体
        MultipartBody.Part imagePart = null;
        if (portraitPath != null) {
            try {
                // 根据图片路径构造一个file对象
                File imageFile = new File(portraitPath);

                // 首先创建一个RequestBody对象
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);

                // 再根据这里的RequestBody对象创建一个MultipartBody.Part
                // 这里的image是和后端约定好的key
                imagePart = MultipartBody.Part.createFormData("img",
                        imageFile.getName(), requestFile);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String, RequestBody> bodyMap = new HashMap<>();
        bodyMap.put("UserName", toRequestBody(infoUpdateModel.getUsername()));
        bodyMap.put("Nickname", toRequestBody(infoUpdateModel.getNickname()));
        bodyMap.put("Sign", toRequestBody(infoUpdateModel.getSignature()));
        bodyMap.put("PassWord", toRequestBody(infoUpdateModel.getPassword()));


        Call<BaseResponse> call = remoteService.updateUserInfo(bodyMap, imagePart);
        call.enqueue(callback);
    }

    /**
     * 根据用户id查询用户所有信息
     *
     *
     * @param userId 用户的id
     * @param callback 回调函数
     */
    public static void getUserInfoByUserId(String userId, Callback<UserResponse> callback) {
        RemoteService remoteService = Network.remote();
        Call<UserResponse> call = remoteService.getUserInfo(new UserIdModel(userId));
        call.enqueue(callback);
    }

    /**
     * 根据用户id查询用户的所有朋友圈
     *
     *
     * @param userId 用户id
     * @param callback 回调函数
     */
    public static void getAllMomentsByUserId(String userId, Callback<MomentResponse> callback) {
        RemoteService remoteService = Network.remote();
        Call<MomentResponse> call = remoteService.getUserAllMoments(new UserIdModel(userId));
        call.enqueue(callback);
    }

}
