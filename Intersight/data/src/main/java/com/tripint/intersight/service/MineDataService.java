package com.tripint.intersight.service;


import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.mine.FocusEntity;
import com.tripint.intersight.entity.mine.InterviewEntity;
import com.tripint.intersight.entity.mine.MineFollowPointEntity;
import com.tripint.intersight.entity.mine.UserHomeEntity;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 个人中心请求服务
 * Created by lirichen on 16/9/12.
 */
public interface MineDataService {

    //
    @FormUrlEncoded
    @POST("mobileFeedback")
    Observable<BaseResponse<List<String>>> postFeedback(@Field("content") String content);

    @GET("userHome")
    Observable<BaseResponse<UserHomeEntity>> getUserHome();


    //我的观点列表
    @GET("myPoint")
    Observable<BaseResponse<List<MineFollowPointEntity>>> getMyPointList(@Query("page") int page, @Query("size") int size);

    //我关注的观点列表
    @GET("myFollowPoint")
    Observable<BaseResponse<List<MineFollowPointEntity>>> getMyFollowPointList(@Query("page") int page, @Query("size") int size);

    //我的问答
    @GET("mine/discuss")
    Observable<BaseResponse<List<DiscussEntiry>>> getMyAskAnswer(@Query("page") int page, @Query("size") int size);

    //我关注的问答
    @GET("mine/discuss/follow")
    Observable<BaseResponse<List<DiscussEntiry>>> getMyFocusedAskAnswer(@Query("page") int page, @Query("size") int size);

    //我的关注
    @GET("myFollow")
    Observable<BaseResponse<List<FocusEntity>>> getMyFollow(@Query("page") int page, @Query("size") int size);

    //被关注
    @GET("myByFollow")
    Observable<BaseResponse<List<FocusEntity>>> getMyByFollow(@Query("page") int page, @Query("size") int size);

    //我的访谈
    @GET("appointmentInterview")
    Observable<BaseResponse<List<InterviewEntity>>> getMyInterview(@Query("page") int page, @Query("size") int size);
}
