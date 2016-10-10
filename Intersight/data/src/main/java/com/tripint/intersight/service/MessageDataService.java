package com.tripint.intersight.service;

import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.discuss.DiscussPageEntity;

import java.util.List;

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
    Observable<BaseResponse<List<DiscussPageEntity>>> getNewMessage(@Query("page") int page, @Query("size") int size);

    //访谈消息
    @GET("interview")
    Observable<BaseResponse<List<DiscussPageEntity>>> getInterviewMessage(@Query("page") int page, @Query("size") int size);

    //问答消息
    @GET("interview")
    Observable<BaseResponse<List<DiscussPageEntity>>> getAskAnswerMessage(@Query("page") int page, @Query("size") int size);

    //评论或赞消息
    @GET("interview")
    Observable<BaseResponse<List<DiscussPageEntity>>> getCommentAgreeMessage(@Query("page") int page, @Query("size") int size);

    //评论或赞消息
    @GET("interview")
    Observable<BaseResponse<List<DiscussPageEntity>>> getSystemMessage(@Query("page") int page, @Query("size") int size);
}
