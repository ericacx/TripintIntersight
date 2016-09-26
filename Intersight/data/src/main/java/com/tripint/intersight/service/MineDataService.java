package com.tripint.intersight.service;


import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.ForgetPasswordEntity;
import com.tripint.intersight.entity.discuss.DiscussDetailEntiry;
import com.tripint.intersight.entity.discuss.DiscussPageEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 个人中心请求服务
 * Created by lirichen on 16/9/12.
 */
public interface MineDataService {

    //忘记密码
    @FormUrlEncoded
    @POST("mobileFeedback")
    Observable<BaseResponse<String>> postFeedback(@Field("content") String content);
}
