package com.example.tang.wuhua.net.service;


import com.example.tang.wuhua.model.parameter.LoginModel;
import com.example.tang.wuhua.model.parameter.RegisterModel;
import com.example.tang.wuhua.model.parameter.CommentModel;
import com.example.tang.wuhua.model.parameter.MomentModel;
import com.example.tang.wuhua.model.parameter.InfoUpdateModel;
import com.example.tang.wuhua.model.response.BaseResponse;
import com.example.tang.wuhua.model.response.CommentResponse;
import com.example.tang.wuhua.model.response.LikeResponse;
import com.example.tang.wuhua.model.response.MomentResponse;
import com.example.tang.wuhua.model.response.UserResponse;

import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;


/**
 * 一些Retrofit的说明：
 * --------------------
 * 注意：注解中的是相对主机的url地址
 * （如/Register就代表主机根目录下的Register目录，所以这里我们前面都不加"/"）
 *
 * Multipart注解表示该请求体是一个支持文件上传的表单
 * 与参数Part和PartMap配合使用
 * PartMap接受的数据类型为Map<String, ResquestBody>.
 *
 * 另外，FormUrlEncoded注解表示请求体是一个（平平无奇）的表单
 * 与参数Field和FieldMap配合使用
 * FieldMap接受的数据类型为Map<String, String>.
 *
 * Query它们的功能和Field它们是一样的
 * 不同之处在于Query它们的请求参数会体现在请求行上（HTTP报文最前面一行，用?拼接的那种）
 * Field的参数在请求体中
 *
 * MultipartBody是RequestBody的子类
 * RequestBody被用于json/String请求
 * MultipartBody被用于文件请求
 *--------------------
 *
 * @author z1ycheng
 * 我们自己的服务器服务的接口
 */
public interface RemoteService {

    /**
     * 注册
     * @param model 用户的注册信息
     * @return 返回BaseResponse 仅包含状态信息
     */
    @POST("Register") // 根据实际修改，下同
    Call<BaseResponse> register(@Body RegisterModel model);

    /**
     * 登录(参数按json传递)
     * @param model 用户的登录请求信息
     * @return 返回UserResponse
     */
    @POST("Login")
    Call<UserResponse> login(@Body LoginModel model);

    /**
     * 登录(参数直接传递)
     * @param username 用户名
     * @param password 密码
     * @return 返回响应的UserResponse
     */
    @POST("Login")
    Call<UserResponse> login(@Query("UserName") String username,
                             @Query("PassWord") String password);


    /**
     * 刷新，下拉获取更多moment
     * @param latitude 纬度
     * @param longitude 经度
     * @param lastMomentTime 最后一条朋友圈发布的时间
     * @return 一个list 如果时间为空返回10条最新的消息(少于10条则返回全部);
     * 不为空返回直到lastMomentTime最新的10条（同上）
     */
    @GET("RefreshMoments")
    Call<MomentResponse> refreshMoments(@Query("Latitude") double latitude,
                                        @Query("Longitude") double longitude,
                                        @Query("Time") Date lastMomentTime);

    /**
     * 获取一个moment下所有的评论
     * @param momentId moment的id
     * @return 一个list 包含该条朋友圈下面的所有评论
     */
    @GET("GetComments")
    Call<CommentResponse> getComments(@Query("Mid") String momentId);

    /**
     * 获得一个moment下的所有赞
     * @param momentId moment的id
     * @return 一个list 包含该条朋友圈下面的所有赞
     */
    @GET("GetLikes")
    Call<LikeResponse> getLikes(@Query("Mid") String momentId);

    /**
     * 发送moment
     * @param momentModel 包含moment的必要信息
     * @return BaseResponse仅包含状态信息
     */
    @POST("PublishMoment")
    Call<BaseResponse> publishMoment(@Body MomentModel momentModel);

    /**
     * 发送moment(可选)
     * @param userId 用户id
     * @param latitude 纬度
     * @param longitude 经度
     * @param text 文本
     * @param imageParts 图片part
     * @param videoParts 视频part
     * @param publishTime 发布时间
     * @param location 发布地点
     * @return BaseResponse仅包含状态信息
     */
    @Multipart
    @POST("PublishMoment")
    Call<BaseResponse> publishMoment(@Part("Uid") String userId,
                                     @Part("LocY") double latitude,
                                     @Part("LocX") double longitude,
                                     @Part("Text_m") String text,
                                     @Part List<MultipartBody.Part> imageParts,
                                     @Part List<MultipartBody.Part> videoParts,
                                     @Part("Time_m") Date publishTime,
                                     @Part("Loc_Des") String location);

    /**
     * 发送评论
     * @param commentModel 包含评论的必要信息
     * @return BaseResponse仅包含状态信息
     */
    @POST("PublishComment")
    Call<BaseResponse> publishComment(@Body CommentModel commentModel);


    /**
     * 点赞
     * @param userId 点赞人的id
     * @param momentId 点赞的moment的id
     * @param likeTime 点赞的时间
     * @return BaseResponse仅包含状态信息
     */
    @POST("Like")
    Call<BaseResponse> like(@Query("Uid") String userId,
                            @Query("Mid") String momentId,
                            @Query("Time") Date likeTime);

    /**
     * 取消点赞
     * @param userId 取消点赞人的id
     * @param momentId 取消点赞的moment的id
     * @return BaseResponse仅包含状态信息
     */
    @POST("LikeCancel")
    Call<BaseResponse> likeCancel(@Query("Uid") String userId,
                                  @Query("Mid") String momentId);


    /**
     * 用户修改个人信息
     * @param infoUpdateModel 用户的model
     * @return BaseResponse仅包含状态信息
     */
    @POST("Update")
    Call<BaseResponse> updateUserInfo(@Body InfoUpdateModel infoUpdateModel);


    /**
     * 用户修改个人信息（可选）
     * @param username 用户名
     * @param nickname 昵称
     * @param image 图片
     * @param signature 签名
     * @return BaseResponse仅包含状态信息
     */
    @Multipart
    @POST("Update")
    Call<BaseResponse> updateUserInfo(@Part("UserName") String username,
                                      @Part("Nickname") String nickname,
                                      @Part MultipartBody.Part image, // 这个地方@Path后面不能加东西
                                      @Part("Sign") String signature);

    /**
     * 根据用户id查询用户信息
     * @param userId 某个用户的id
     * @return 该用户的所有信息
     */
    @GET("/")
    Call<UserResponse> getUserInfo(@Query("Uid") String userId);

    /**
     * 根据用户id查询该用户发布的所有moment
     * @param userId 某个用户的id
     * @return 一个list 里面包含了该用户的所有的moment
     */
    @GET("/")
    Call<MomentResponse> getUserAllMoments(@Query("Uid") String userId);

}
