package com.tripint.intersight.service;

import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.discuss.DiscussEntiry;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 消息请求服务
 * Created by Eric on 16/10/10.
 */

public interface MessageDataService {

    //新消息
    @GET("interview")
    Observable<BaseResponse<BasePageableResponse<DiscussEntiry>>> getNewMessage(@Query("page") int page, @Query("size") int size);

    //访谈消息
    @GET("interview")
    Observable<BaseResponse<BasePageableResponse<DiscussEntiry>>> getInterviewMessage(@Query("page") int page, @Query("size") int size);

    //问答消息
    @GET("interview")
    Observable<BaseResponse<BasePageableResponse<DiscussEntiry>>> getAskAnswerMessage(@Query("page") int page, @Query("size") int size);

    //评论或赞消息
    @GET("interview")
    Observable<BaseResponse<BasePageableResponse<DiscussEntiry>>> getCommentAgreeMessage(@Query("page") int page, @Query("size") int size);

    //评论或赞消息
    @GET("interview")
    Observable<BaseResponse<BasePageableResponse<DiscussEntiry>>> getSystemMessage(@Query("page") int page, @Query("size") int size);
}
