package com.example.tang.wuhua.net.service;


import com.example.tang.wuhua.model.parameter.LikeCancelModel;
import com.example.tang.wuhua.model.parameter.LikeModel;
import com.example.tang.wuhua.model.parameter.LoginModel;
import com.example.tang.wuhua.model.parameter.MomentIdModel;
import com.example.tang.wuhua.model.parameter.RefreshModel;
import com.example.tang.wuhua.model.parameter.RegisterModel;
import com.example.tang.wuhua.model.parameter.CommentModel;
import com.example.tang.wuhua.model.parameter.MomentModel;
import com.example.tang.wuhua.model.parameter.InfoUpdateModel;
import com.example.tang.wuhua.model.parameter.UserIdModel;
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
 * 全部使用post方法
 * 除上传文件外全部使用json格式（仅一个参数也是json）
 */
public interface RemoteService {

    /**
     * 注册
     * OK
     *
     * @param model 用户的注册信息
     * @return 返回BaseResponse 仅包含状态信息
     */
    @POST("Register") // 根据实际修改，下同
    Call<BaseResponse> register(@Body RegisterModel model);

    /**
     * 登录
     * OK
     *
     * @param model 用户的登录请求信息
     * @return 返回UserResponse
     */
    @POST("Login")
    Call<UserResponse> login(@Body LoginModel model);

    /**
     * 刷新，下拉获取更多moment
     *
     *
     * @return 一个list 如果时间为空返回10条最新的消息(少于10条则返回全部);
     * 不为空返回直到lastMomentTime最新的10条（同上）
     */
    @POST("GetNewMoment")
    Call<MomentResponse> refreshMoments(@Body RefreshModel refreshModel);

    /**
     * 获取一个moment下所有的评论
     *
     *
     * @param momentIdModel moment的id的model
     * @return 一个list 包含该条朋友圈下面的所有评论
     */
    @POST("GetComment")
    Call<CommentResponse> getComments(@Body MomentIdModel momentIdModel);

    /**
     * 获得一个moment下的所有赞
     * OK
     *
     * @param momentIdModel moment的id的model
     * @return 一个list 包含该条朋友圈下面的所有赞
     */
    @POST("GetLike")
    Call<LikeResponse> getLikes(@Body MomentIdModel momentIdModel);


    /**
     * 发送朋友圈
     *
     *
     * @param map 包含用户id，经度，纬度，文本，发布时间，发布地点，媒体文件的类型
     * @param mediaParts 图片或者视频
     * @return 发送是否成功
     */
    @Multipart
    @POST("DoMoment")
    Call<BaseResponse> publishMoment(@PartMap Map<String, RequestBody> map,
                                     @Part List<MultipartBody.Part> mediaParts);

    /**
     * 发送评论
     *
     *
     * @param commentModel 包含评论的必要信息
     * @return BaseResponse仅包含状态信息
     */
    @POST("DoComment")
    Call<BaseResponse> publishComment(@Body CommentModel commentModel);


    /**
     * 点赞
     * OK
     *
     * @param likeModel 点赞的model
     * @return BaseResponse仅包含状态信息 表示点赞是否成功
     */
    @POST("DoLike")
    Call<BaseResponse> like(@Body LikeModel likeModel);

    /**
     * 取消点赞
     *
     *
     * @param likeCancelModel 取消点赞的model
     * @return BaseResponse仅包含状态信息
     */
    @POST("UndoLike")
    Call<BaseResponse> likeCancel(@Body LikeCancelModel likeCancelModel);


    /**
     * 修改用户个人信息
     * OK
     *
     * @param map 包含用户名 昵称 签名 密码
     * @param image 图片
     * @return BaseResponse 表示修改是否成功
     */
    @Multipart
    @POST("UpdateUserInfo")
    Call<BaseResponse> updateUserInfo(@PartMap Map<String, RequestBody> map,
                                        @Part MultipartBody.Part image);
    /**
     * 根据用户id查询用户信息
     *
     *
     * @param userIdModel 某个用户的id
     * @return 该用户的所有信息
     */
    @POST("GetUserInfo")
    Call<UserResponse> getUserInfo(@Body UserIdModel userIdModel);

    /**
     * 根据用户id查询该用户发布的所有moment
     *
     *
     * @param userIdModel 某个用户的id
     * @return 一个list 里面包含了该用户的所有的moment
     */
    @POST("GetUserMoment")
    Call<MomentResponse> getUserAllMoments(@Body UserIdModel userIdModel);

}
