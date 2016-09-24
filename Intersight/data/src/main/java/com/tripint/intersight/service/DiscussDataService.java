package com.tripint.intersight.service;


import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.discuss.DiscussDetailEntiry;
import com.tripint.intersight.entity.discuss.DiscussPageEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lirichen on 16/9/12.
 */
public interface DiscussDataService {

    @GET("discuss")
    Observable<BaseResponse<DiscussPageEntity>> getDiscuss(@Query("page") int page, @Query("size") int size);

    @GET("mine/discuss")
    Observable<BaseResponse<DiscussPageEntity>> getSearchFilter(@Query("type") String type);

    @GET("discuss/{id}")
    Observable<BaseResponse<DiscussDetailEntiry>> getDiscussDetail(@Path("id") int id);
}
