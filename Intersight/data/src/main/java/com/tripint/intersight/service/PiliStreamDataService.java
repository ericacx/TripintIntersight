package com.tripint.intersight.service;


import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.stream.SaveStreamResponseEntity;
import com.tripint.intersight.entity.stream.StreamResponseEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lirichen on 16/9/12.
 */
public interface PiliStreamDataService {


    //获取推送URL
    @GET("audio/stream")
    Observable<BaseResponse<StreamResponseEntity>> postPublishStreamUrl();

    //获取推送URL
    @FormUrlEncoded
    @POST("audio/store")
    Observable<BaseResponse<SaveStreamResponseEntity>> postSavePublishStream(@Field("streamId") String streamId,
                                                                             @Field("discussId") int discussId,
                                                                             @Field("time") int timeLong);
}
