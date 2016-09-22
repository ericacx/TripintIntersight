package com.tripint.intersight.service;


import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.Industry;
import com.tripint.intersight.entity.SearchFilterEntity;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lirichen on 16/9/12.
 */
public interface BaseDataService {

    @GET("industry")
    Observable<BaseResponse<List<Industry>>> getIndustry(@Query("start") int start, @Query("count") int count);

    @GET("filter")
    Observable<BaseResponse<SearchFilterEntity>> getSearchFilter(@Query("type") String type);
}
