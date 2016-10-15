package com.tripint.intersight.service;

import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.message.MessageDataEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 消息请求服务
 * Created by Eric on 16/10/10.
 */

public interface MessageDataService {

    //新消息
    @GET("newMessageList")
    Observable<BaseResponse<BasePageableResponse<MessageDataEntity>>> getNewMessage(@Query("page") int page, @Query("size") int size);

    //访谈消息
    @GET("interviewMessage")
    Observable<BaseResponse<BasePageableResponse<MessageDataEntity>>> getInterviewMessage(@Query("page") int page, @Query("size") int size);

    //问答消息
    @GET("questionMessage")
    Observable<BaseResponse<BasePageableResponse<MessageDataEntity>>> getAskAnswerMessage(@Query("page") int page, @Query("size") int size);

    //评论或赞消息
    @GET("commentMessage")
    Observable<BaseResponse<BasePageableResponse<MessageDataEntity>>> getCommentPraiseMessage(@Query("page") int page, @Query("size") int size);

    //评论或赞消息
    @GET("interview")
    Observable<BaseResponse<BasePageableResponse<MessageDataEntity>>> getSystemMessage(@Query("page") int page, @Query("size") int size);
}
