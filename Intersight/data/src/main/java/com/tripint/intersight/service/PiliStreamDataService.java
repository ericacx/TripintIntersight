package com.tripint.intersight.service;


import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.stream.StreamResponseEntity;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by lirichen on 16/9/12.
 */
public interface PiliStreamDataService {


    //获取推送URL
    @GET("audio/stream")
    Observable<BaseResponse<StreamResponseEntity>> postPublishStreamUrl();

}
