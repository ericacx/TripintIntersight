package com.tripint.intersight.service;


import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.Industry;
import com.tripint.intersight.entity.SearchFilterEntity;
import com.tripint.intersight.entity.UserInfoEntity;
import com.tripint.intersight.entity.article.ArticleBannerEntity;
import com.tripint.intersight.entity.article.ArticlesEntity;
import com.tripint.intersight.entity.common.CommonResponEntity;
import com.tripint.intersight.entity.user.ChooseEntity;
import com.tripint.intersight.entity.user.LoginEntity;
import com.tripint.intersight.entity.user.RegisterEntity;
import com.tripint.intersight.entity.user.User;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lirichen on 16/9/12.
 */
public interface BaseDataService {


    @GET("industry")
    Observable<BaseResponse<List<Industry>>> getIndustry(@Query("start") int start, @Query("count") int count);

    @GET("filter")
    Observable<BaseResponse<SearchFilterEntity>> getSearchFilter(@Query("type") String type);

    //注册
    @POST("register")
    Observable<BaseResponse<RegisterEntity>> postRegister(
            @Body User user
    );

    //登录
    @POST("login")
    Observable<BaseResponse<LoginEntity>> postLogin(
            @Body User user
    );

//    @FormUrlEncoded
//    @POST("login")
//    Observable<BaseResponse<LoginEntity.UserInfoBean>> getLogin(
//            @Field("email") String email,
//            @Field("password") String password
//
//    );


    //手机发送短信
    @GET("send/code")
    Observable<BaseResponse<CodeDataEntity>> getCode(@Query("mobile") String mobile);

    //忘记密码
    @FormUrlEncoded
    @POST("password/forget")
    Observable<BaseResponse<CodeDataEntity>> postPasswordForget(@Field("email") String email);

    //重置密码
    @POST("password/reset")
    Observable<BaseResponse<CodeDataEntity>> postResetpassword(
            @Body User user);

    //填写个人信息
    @POST("user/info")
    Observable<BaseResponse<UserInfoEntity>> postUserInfo(
            @Body UserInfoEntity userInfoEntity
    );

    //选择角色
    @FormUrlEncoded
    @POST("choose/role")
    Observable<BaseResponse<ChooseEntity>> postChooseRole(@Field("role") String role);

    //关注的行业
    @FormUrlEncoded
    @POST("interest/industry")
    Observable<BaseResponse<ChooseEntity>> postInterestIndustry(
            @Field("industryId") String strings
    );

    //观点列表
    @GET("articles")
    Observable<BaseResponse<BasePageableResponse<ArticlesEntity>>> getArticles(
            @Query("page") int page,
            @Query("size") int size
    );

    //观点banner
    @GET("banner")
    Observable<BaseResponse<ArticleBannerEntity>> getArticleBanner(@Query("type") int type);

    //观点banner
    @GET("banner")
    Observable<BaseResponse<ArticleBannerEntity>> getBanner(@Query("type") int type);


    //验证用户是否存在
    @GET("user/check")
    Observable<BaseResponse<CommonResponEntity>> checkUserPhoneExist(@Query("email") String email);

    //忘记密码
    @FormUrlEncoded
    @POST("share/login")
    Observable<BaseResponse<CommonResponEntity>> postShareLogin(@Field("type") String type,
                                                                @Field("openid") String openid,
                                                                @Field("unionid") String unionid,
                                                                @Field("headImgUrl") String imgUrl,
                                                                @Field("nickname") String nickname,
                                                                @Field("email") String email,
                                                                @Field("code") String code,
                                                                @Field("password") String password,
                                                                @Field("action") String action);

}
