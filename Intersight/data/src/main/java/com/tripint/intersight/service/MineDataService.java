package com.tripint.intersight.service;


import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.PersonalUserInfoEntity;
import com.tripint.intersight.entity.discuss.CreateInterviewResponseEntity;
import com.tripint.intersight.entity.mine.AccountDetailEntity;
import com.tripint.intersight.entity.mine.AskAnswerEntity;
import com.tripint.intersight.entity.mine.BalanceEntity;
import com.tripint.intersight.entity.mine.FocusEntity;
import com.tripint.intersight.entity.mine.HelpAndProtocolEntity;
import com.tripint.intersight.entity.mine.InterviewDetailEntity;
import com.tripint.intersight.entity.mine.InterviewEntity;
import com.tripint.intersight.entity.mine.MineFollowPointEntity;
import com.tripint.intersight.entity.mine.PersonalUserHomeEntity;
import com.tripint.intersight.entity.mine.QiniuTokenEntity;
import com.tripint.intersight.entity.mine.UserHomeEntity;
import com.tripint.intersight.entity.mine.worker.AllResoucesEntity;
import com.tripint.intersight.entity.mine.worker.EditUserEntity;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 个人中心请求服务
 * Created by lirichen on 16/9/12.
 */
public interface MineDataService {

    //意见反馈
    @FormUrlEncoded
    @POST("mobileFeedback")
    Observable<BaseResponse<List<String>>> postFeedback(@Field("content") String content);

    //个人中心首页
    @GET("userHome")
    Observable<BaseResponse<UserHomeEntity>> getUserHome();

    //我的观点列表
    @GET("myPoint")
    Observable<BaseResponse<BasePageableResponse<MineFollowPointEntity>>> getMyPointList(@Query("page") int page, @Query("size") int size);

    //我关注的观点列表
    @GET("myFollowPoint")
    Observable<BaseResponse<BasePageableResponse<MineFollowPointEntity>>> getMyFollowPointList(@Query("page") int page, @Query("size") int size);

    //我的问答
    @GET("mine/discuss")
    Observable<BaseResponse<BasePageableResponse<AskAnswerEntity>>> getMyAskAnswer(@Query("page") int page, @Query("size") int size);

    //我关注的问答
    @GET("mine/discuss/follow")
    Observable<BaseResponse<BasePageableResponse<AskAnswerEntity>>> getMyFocusedAskAnswer(@Query("page") int page, @Query("size") int size);

    //我的关注
    @GET("myFollow")
    Observable<BaseResponse<BasePageableResponse<FocusEntity>>> getMyFollow(@Query("page") int page, @Query("size") int size);

    //被关注
    @GET("myByFollow")
    Observable<BaseResponse<BasePageableResponse<FocusEntity>>> getMyByFollow(@Query("page") int page, @Query("size") int size);

    //我的访谈
    @GET("appointmentInterview")
    Observable<BaseResponse<BasePageableResponse<InterviewEntity>>> getMyInterview(@Query("page") int page, @Query("size") int size);

//    //访谈详情
//    @GET("myInterview/{id}")
//    Observable<BaseResponse<InterviewDetailEntity>> getInterviewDetail(@Path("id") int id);

    //访谈详情
    @GET("myInterview/{id}")
    Observable<BaseResponse<InterviewDetailEntity>> getInterviewDetail(@Path("id") int id);

    //账户明细
    @GET("accountList")
    Observable<BaseResponse<BasePageableResponse<AccountDetailEntity>>> getAccountDetail(@Query("page") int page, @Query("size") int size);

    //账户余额
    @POST("user/balance")
    Observable<BaseResponse<BalanceEntity>> postUserBalance();

    //修改密码
    @FormUrlEncoded
    @POST("changePassword")
    Observable<BaseResponse<CodeDataEntity>> postChangePassword(
            @Field("oldPassword") String oldPassword,
            @Field("password") String password,
            @Field("repassword") String repassword
            );

    //修改个人资料
    @POST("editUser")
    Observable<BaseResponse<CodeDataEntity>> postEditUser(@Body EditUserEntity editUserEntity);

    //邮箱发送验证码
    @GET("sendEmail")
    Observable<BaseResponse<CodeDataEntity>> getSendEmail(@Query("email") String email);

    //修改邮箱
    @FormUrlEncoded
    @POST("changeEmail")
    Observable<BaseResponse<CodeDataEntity>> postChangeEmail(
            @Field("email") String email,
            @Field("code") int code
    );

    //修改手机
    @FormUrlEncoded
    @POST("changeMobile")
    Observable<BaseResponse<CodeDataEntity>> postChangeMobile(
            @Field("mobile") String mobile,
            @Field("code") int code
    );

    //向他人提问
    @FormUrlEncoded
    @POST("otherQuestion")
    Observable<BaseResponse<CodeDataEntity>> postOtherQuestion(
            @Field("from_uid") int from_uid,
            @Field("content") String content
    );

    //向他人约访
    @POST("otherInterview")
    Observable<BaseResponse<CreateInterviewResponseEntity>> postOtherInterview(
            @Body PersonalUserInfoEntity personalUserInfoEntity
            );


    //职员,学生信息
    @GET("allResources")
    Observable<BaseResponse<AllResoucesEntity>> getAllResources(@Query("type") int type);

    //更新用户头像
    @FormUrlEncoded
    @POST("update/avatar")
    Observable<BaseResponse<CodeDataEntity>> postUpdateAvatar(
            @Field("uploadKey") String uploadKey
    );

    //七牛云token
    @GET("token/qiniu")
    Observable<BaseResponse<QiniuTokenEntity>> getQiniuToken();

    //使用帮助,用户协议
    @GET("sys/config")
    Observable<BaseResponse<HelpAndProtocolEntity>> getUserHelpAndProtocol();
}
