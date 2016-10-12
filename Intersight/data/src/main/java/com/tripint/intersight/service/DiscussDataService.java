package com.tripint.intersight.service;


import com.tripint.intersight.common.BasePageableResponse;
import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.discuss.CommentResultEntity;
import com.tripint.intersight.entity.discuss.DiscussDetailEntity;
import com.tripint.intersight.entity.discuss.DiscussEntiry;
import com.tripint.intersight.entity.discuss.Specialist;

import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lirichen on 16/9/12.
 */
public interface DiscussDataService {

    //观点
    @GET("discuss")
    Observable<BaseResponse<BasePageableResponse<DiscussEntiry>>> getDiscuss(@Query("page") int page, @Query("size") int size);

    @GET("mine/discuss")
    Observable<BaseResponse<BasePageableResponse<DiscussEntiry>>> getSearchFilter(@Query("type") String type);

    @GET("discuss/{id}")
    Observable<BaseResponse<DiscussDetailEntity>> getDiscussDetail(@Path("id") int id);

    @PUT("discuss/{id}")
    Observable<BaseResponse<CommentResultEntity>> createComment(@Path("id") int id, @Query("action") String action, @Query("type") String type, @Query("content") String content);

    @PUT("discuss/{id}")
    Observable<BaseResponse<CommentResultEntity>> createSubComment(@Path("id") int id, @Query("action") String action,
                                                      @Query("type") String type,
                                                      @Query("content") String content,
                                                      @Query("commentId") int commentId,
                                                      @Query("toNickname") String toNickname);

    @PUT("discuss/{id}")
    Observable<BaseResponse<CommentResultEntity>> createPraises(@Path("id") int id, @Query("action") String action, @Query("type") String type);

    @PUT("discuss/{id}")
    Observable<BaseResponse<CommentResultEntity>> deletePraises(@Path("id") int id, @Query("action") String action, @Query("type") String type);

    @PUT("discuss/{id}")
    Observable<BaseResponse<CommentResultEntity>> createFollow(@Path("id") int id, @Query("action") String action, @Query("type") String type);

    @PUT("discuss/{id}")
    Observable<BaseResponse<CommentResultEntity>> deleteFollow(@Path("id") int id, @Query("action") String action, @Query("type") String type);

    //提问
    @POST("questionsShow")
    Observable<BaseResponse<BasePageableResponse<Specialist>>> getSpecialists(@Field("page") int page, @Field("size") int size);
}
