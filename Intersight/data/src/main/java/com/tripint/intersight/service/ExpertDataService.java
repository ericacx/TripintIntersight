package com.tripint.intersight.service;

import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.mine.FocusEntity;
import com.tripint.intersight.entity.mine.InterviewDetailEntity;
import com.tripint.intersight.entity.mine.InterviewEntity;
import com.tripint.intersight.entity.mine.MineFollowPointEntity;
import com.tripint.intersight.entity.mine.UserHomeEntity;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 他的个人中心请求服务
 * Created by Eric on 16/10/11.
 */

public interface ExpertDataService {

    //个人中心首页
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

    //访谈详情
    @GET("myInterview/{id}")
    Observable<BaseResponse<InterviewDetailEntity>> getInterviewDetail(@Path("id") int id);

}
