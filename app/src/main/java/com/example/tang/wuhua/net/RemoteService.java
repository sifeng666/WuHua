package com.example.tang.wuhua.net;


import com.example.tang.wuhua.test.ReportTest;
import com.example.tang.wuhua.model.api.account.LoginModel;
import com.example.tang.wuhua.model.api.account.RegisterModel;
import com.example.tang.wuhua.model.Result;
import com.example.tang.wuhua.model.api.comment.CommentModel;
import com.example.tang.wuhua.model.api.info.InfoUpdateModel;
import com.example.tang.wuhua.model.api.moment.MomentModel;
import com.example.tang.wuhua.model.card.CommentCard;
import com.example.tang.wuhua.model.card.MomentCard;
import com.example.tang.wuhua.model.card.UserCard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Retrofit2 的所有Service
 * @author z1ycheng
 */


public interface RemoteService {

    /**
     * 注册请求的接口
     * @param model 用户的注册信息
     * @return 返回注册Result<UserCard>
     */
    @POST("user/register") // TODO 根据实际修改，下同
    Call<Result<UserCard>> register(@Body RegisterModel model);

    /**
     * 登录请求的接口
     * @param model 用户的登录请求信息
     * @return 返回登录Result<UserCard>
     */
    @POST("user/login")
    Call<Result<UserCard>> login(@Body LoginModel model);

    /**
     * 根据用户id查询用户信息
     * @param userId 用户id
     * @return Result<UserCard>
     */
    @GET("user/infoupdate")
    Call<Result<UserCard>> infoQuery(@Path("userId") String userId);

    /**
     * 用户更新个人信息的接口
     * @param model 用户更新的参数Model
     * @return Result<UserCard>
     */
    @POST("user/infoupdate")
    Call<Result<UserCard>> infoUpdate(@Body InfoUpdateModel model);

    /**
     * 附近的朋友圈的接口，用于显示在首页
     * @param userId 当前登录的用户id
     * @return 返回附近符合条件的Result<List<MomentCard>>
     */
    @GET("moment/nearby/{userId}")
    Call<Result<List<MomentCard>>> nearbyMoments(@Path("userId") String userId);

    /**
     * 根据朋友圈id拉取其下方所有的评论
     * @param momentId 当前朋友圈的id
     * @return 返回符合条件的Result<List<CommentCard>>
     */
    @GET("moment/comment/{momentId}")
    Call<Result<List<CommentCard>>> allComments(@Path("momentId") String momentId);

    /**
     * 根据用户id查看其发布的所有朋友圈
     * @param userId 用户的id
     * @return 返回符合条件的Result<List<MomentCard>>
     */
    @GET("moment/{userId}")
    Call<Result<List<MomentCard>>> allMoments(@Path("userId") String userId);

    /**
     * 发送朋友圈的接口
     * @param model 用户发送朋友圈的model
     * @return Result<MomentCard>
     */
    @POST("user/publish")
    Call<Result<MomentCard>> publish(@Body MomentModel model);

    /**
     * 用户发送评论的接口
     * @param model 用户发送评论的model
     * @return
     */
    @POST("user/comment")
    Call<Result<CommentCard>> publish(@Body CommentModel model);


    /**
     * 乐帮的服务器测试了retrofit和gson功能
     */
    // @GET("user/mypublish")
    // Call<List<ReportTest>> mytask(@Query("user_id") int user_id);

}
