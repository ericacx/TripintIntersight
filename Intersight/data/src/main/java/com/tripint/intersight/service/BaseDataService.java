package com.tripint.intersight.service;


import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.CodeDataEntity;
import com.tripint.intersight.entity.ForgetPasswordEntity;
import com.tripint.intersight.entity.Industry;
import com.tripint.intersight.entity.SearchFilterEntity;
import com.tripint.intersight.entity.UserInfoEntity;
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
    Observable<BaseResponse<LoginEntity.UserInfoBean>> postLogin(
            @Body User user
    );


    //手机发送短信
    @GET("send/code")
    Observable<BaseResponse<CodeDataEntity>> getCode(@Query("mobile") String mobile);

    //忘记密码
    @FormUrlEncoded
    @POST("password/forget")
    Observable<BaseResponse<ForgetPasswordEntity>> postForgetpassword(@Field("mobile") String mobile);

    //填写个人信息
    @POST("user/info")
    Observable<BaseResponse<UserInfoEntity>> postUserInfo(
            @Body UserInfoEntity userInfoEntity
    );

    //选择角色
    @FormUrlEncoded
    @POST("choose/role")
    Observable<BaseResponse<CodeDataEntity>> postChooseRole(@Field("role") String role);
}
