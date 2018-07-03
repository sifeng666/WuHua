package com.example.tang.wuhua.net.helper;

import com.example.tang.wuhua.model.parameter.CommentModel;
import com.example.tang.wuhua.model.parameter.InfoUpdateModel;
import com.example.tang.wuhua.model.parameter.LoginModel;
import com.example.tang.wuhua.model.parameter.MomentModel;
import com.example.tang.wuhua.model.parameter.RegisterModel;
import com.example.tang.wuhua.model.response.BaseResponse;
import com.example.tang.wuhua.model.response.CommentResponse;
import com.example.tang.wuhua.model.response.LikeResponse;
import com.example.tang.wuhua.model.response.MomentResponse;
import com.example.tang.wuhua.model.response.UserResponse;
import com.example.tang.wuhua.net.Network;
import com.example.tang.wuhua.net.service.RemoteService;

import java.io.File;
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
     * 注册的封装 异步请求
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
     * @param loginModel 登录model
     * @param callback 回调函数
     */
    public static void login(LoginModel loginModel,  Callback<UserResponse> callback) {
        RemoteService remoteService = Network.remote();
        Call<UserResponse> call =
                remoteService.login(loginModel.getUsername(), loginModel.getPassword());
        call.enqueue(callback);
    }

    /**
     * 获取更多信息
     * @param latitude 当前用户的纬度信息
     * @param longitude 当前用户的经度信息
     * @param isMovingUp 是否从下往上滑动 true表示下滑 false表示上拉
     * @param lastMomentTime 主页面最后一条moment的发布时间
     * @param callback 回调函数
     */
    public static void refreshMoments(double latitude, double longitude,
                                      boolean isMovingUp, Date lastMomentTime,
                                      Callback<MomentResponse> callback ) {

        RemoteService remoteService = Network.remote();

        Date paramDate = lastMomentTime;

        // 如果是上拉操作 刷新主页面获取最新的十条moment显示在主页面上
        if (!isMovingUp) {
            // 此时可以向服务器发送一个在本次请求时间之后的一个日期（如1天后）
            // 服务器在此日期之前最近的10条即是最新的10条
            // 返回一天之后的时间
            long oneDay = 24 * 60 * 60 * 1000;
            paramDate = new Date(System.currentTimeMillis() + oneDay);
        }
        Call<MomentResponse> call =
                remoteService.refreshMoments(latitude, longitude, paramDate);
        call.enqueue(callback);
    }

    /**
     * 根据moment的id查找该moment下的所有评论
     * 异步请求
     * @param momentId moment的id
     * @param callback 回调函数
     */
    public static void getCommentsByMomentId(String momentId, Callback<CommentResponse> callback){
        RemoteService remoteService = Network.remote();
        Call<CommentResponse> call = remoteService.getComments(momentId);
        call.enqueue(callback);
    }

    /**
     * 根据moment的id请求该moment下的所有赞
     * @param momentId moment的id
     * @param callback 回调函数
     */
    public static void getLikesByMomentId(String momentId, Callback<LikeResponse> callback){
        RemoteService remoteService = Network.remote();
        Call<LikeResponse> call = remoteService.getLikes(momentId);
        call.enqueue(callback);
    }

    /**
     * 发布一条朋友圈动态
     * @param momentModel 朋友圈动态model
     * @param callback 回调函数
     */
    public static void publishMoment(MomentModel momentModel, Callback<BaseResponse> callback) {

        // 远程服务
        RemoteService remoteService = Network.remote();

        // 获取路径
        List<String> imagesPaths = momentModel.getImagesPaths();
        List<String> videosPaths = momentModel.getVideosPaths();

        // List<MultipartBody.Part>部分
        List<MultipartBody.Part> imageParts = new ArrayList<>();
        List<MultipartBody.Part> videoParts = new ArrayList<>();

        // 如果需要上传图片
        if (imagesPaths != null && imagesPaths.size() > 0) {
            for (int i = 0; i < imagesPaths.size(); ++ i) {
                try {
                    // 首先根据图片路径构造一个file对象
                    File image = new File(imagesPaths.get(i));

                    // 根据这个图片创建一个RequestBody
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse("multipart/form-data"), image);

                    // 根据这个RequestBody创建一个MultipartBody.Part
                    MultipartBody.Part part = MultipartBody.Part.createFormData("image" + i,
                            image.getName(), requestBody);

                    imageParts.add(part);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 如果需要上传视频
        if (videosPaths != null && videosPaths.size() > 0) {
            for (int i = 0; i < videosPaths.size(); ++ i) {
                try {
                    // 首先根据图片路径构造一个file对象
                    File video = new File(videosPaths.get(i));

                    // 根据这个图片创建一个RequestBody
                    RequestBody requestBody =
                            RequestBody.create(MediaType.parse("multipart/form-data"), video);

                    // 根据这个RequestBody创建一个MultipartBody.Part
                    MultipartBody.Part part = MultipartBody.Part.createFormData("video" + i,
                            video.getName(), requestBody);

                    videoParts.add(part);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Call<BaseResponse> call = remoteService.publishMoment(momentModel.getPublisherId(),
                momentModel.getLatitude(), momentModel.getLongitude(), momentModel.getText(),
                imageParts, videoParts, momentModel.getPublishTime(), momentModel.getLocation());
        call.enqueue(callback);
    }

    /**
     * 发布一条评论
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
     * @param userId 点赞人的id
     * @param momentId 被点赞朋友圈的id
     * @param callback 回调函数
     */
    public static void like(String userId, String momentId, Callback<BaseResponse> callback) {

        RemoteService remoteService = Network.remote();
        Date likeTime = new Date(System.currentTimeMillis()); // 不用手动生成当前时间
        Call<BaseResponse> call = remoteService.like(userId, momentId, likeTime);
        call.enqueue(callback);
    }

    /**
     * 给某条朋友圈取消点赞
     * @param userId 取消点赞人的id
     * @param momentId 取消点赞的朋友圈的id
     * @param callback 回调函数
     */
    public static void likeCancel(String userId, String momentId,
                                  Callback<BaseResponse> callback) {

        RemoteService remoteService = Network.remote();
        Call<BaseResponse> call = remoteService.likeCancel(userId, momentId);
        call.enqueue(callback);
    }

    /**
     * 更新用户信息
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
        MultipartBody.Part imageBody = null;

        // 如果所选择的头像的路径不为空
        if (portraitPath != null) {
            try {
                // 根据图片路径构造一个file对象
                File file = new File(portraitPath);

                // 首先创建一个RequestBody对象
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);

                // 再根据这里的RequestBody对象创建一个MultipartBody.Part
                // 这里的image是和后端约定好的key
                imageBody = MultipartBody.Part.createFormData("image",
                        file.getName(), requestFile);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        Call<BaseResponse> call = remoteService.updateUserInfo(infoUpdateModel.getUsername(),
                infoUpdateModel.getNickname(), imageBody, infoUpdateModel.getSignature());
        call.enqueue(callback);
    }

    /**
     * 根据用户id查询用户所有信息
     * @param userId 用户的id
     * @param callback 回调函数
     */
    public static void getUserInfoByUserId(String userId, Callback<UserResponse> callback) {
        RemoteService remoteService = Network.remote();
        Call<UserResponse> call = remoteService.getUserInfo(userId);
        call.enqueue(callback);
    }

    /**
     * 根据用户id查询用户的所有朋友圈
     * @param userId 用户id
     * @param callback 回调函数
     */
    public static void getAllCommentsByUserId(String userId, Callback<MomentResponse> callback) {
        RemoteService remoteService = Network.remote();
        Call<MomentResponse> call = remoteService.getUserAllMoments(userId);
        call.enqueue(callback);
    }

}
