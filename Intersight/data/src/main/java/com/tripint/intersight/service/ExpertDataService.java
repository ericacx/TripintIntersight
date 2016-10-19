package com.tripint.intersight.service;

import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.mine.AskAnswerEntity;
import com.tripint.intersight.entity.mine.FocusEntity;
import com.tripint.intersight.entity.mine.InterviewDetailEntity;
import com.tripint.intersight.entity.mine.InterviewEntity;
import com.tripint.intersight.entity.mine.MineFollowPointEntity;
import com.tripint.intersight.entity.mine.PersonalUserHomeEntity;
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

    //他的个人中心主页
    @GET("personal/home")
    Observable<BaseResponse<PersonalUserHomeEntity>> getPersonalUserHome(@Query("uid") int uid);

    //他的观点列表
    @GET("myPoint")
    Observable<BaseResponse<BasePageableResponse<MineFollowPointEntity>>> getHisPointList(@Query("uid") int uid,@Query("page") int page,@Query("size") int size);

    //他关注的观点列表
    @GET("myFollowPoint")
    Observable<BaseResponse<BasePageableResponse<MineFollowPointEntity>>> getHisFollowPointList(@Query("uid") int uid,@Query("page") int page,@Query("size") int size);

    //他的问答
    @GET("mine/discuss")
    Observable<BaseResponse<BasePageableResponse<AskAnswerEntity>>> getHisAskAnswer(@Query("uid") int uid, @Query("page") int page, @Query("size") int size);

    //他关注的问答
    @GET("mine/discuss/follow")
    Observable<BaseResponse<BasePageableResponse<AskAnswerEntity>>> getHisFocusedAskAnswer(@Query("uid") int uid,@Query("page") int page,@Query("size") int size);

    //他的关注
    @GET("myFollow")
    Observable<BaseResponse<BasePageableResponse<FocusEntity>>> getHisFollow(@Query("uid") int uid,@Query("page") int page,@Query("size") int size);

    //他被关注
    @GET("myByFollow")
    Observable<BaseResponse<BasePageableResponse<FocusEntity>>> getHisByFollow(@Query("uid") int uid,@Query("page") int page,@Query("size") int size);

    //他的访谈
    @GET("appointmentInterview")
    Observable<BaseResponse<BasePageableResponse<InterviewEntity>>> getHisInterview(@Query("uid") int uid,@Query("page") int page,@Query("size") int size);

    //他访谈详情
    @GET("myInterview/{id}")
    Observable<BaseResponse<InterviewDetailEntity>> getHisInterviewDetail(@Path("id") int id);

}
