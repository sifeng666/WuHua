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

import java.util.Date;

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
    public static void regiser(RegisterModel registerModel, Callback<BaseResponse> callback){
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
            paramDate = new Date(System.currentTimeMillis());
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

        // TODO 需要加一下上传图片的逻辑
        RemoteService remoteService = Network.remote();
        Call<BaseResponse> call = remoteService.publishMoment(momentModel);
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
     * @param likeTime 点赞的时间
     * @param callback 回调函数
     */
    public static void like(String userId, String momentId, Date likeTime,
                            Callback<BaseResponse> callback) {

        RemoteService remoteService = Network.remote();
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

        // TODO 上传图片
        RemoteService remoteService = Network.remote();
        Call<BaseResponse> call = remoteService.updateUserInfo(infoUpdateModel);
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
