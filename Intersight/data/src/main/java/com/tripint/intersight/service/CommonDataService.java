package com.tripint.intersight.service;


import com.tripint.intersight.common.BaseResponse;
import com.tripint.intersight.entity.CommonEntity;
import com.tripint.intersight.entity.discuss.CommentResultEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Eric on 16/10/29.
 */

public interface CommonDataService {

    //点赞,评论,赞
    @FormUrlEncoded
    @POST("common")
    Observable<BaseResponse<CommentResultEntity>> postCommon(
            @Field("itemId") int itemId,//文章id
            @Field("type") int type,//1:文章（观点） 2:问答 3:访谈 4:评论-观点的评论  5 评论 问答的评论
            @Field("action") int action,//1:创建,2:取消
            @Field("toUid") int toUid,//到某个用户的Id
            @Field("content") String content,
            @Field("flg") int flg,//1:评论,2 ,点赞 ,3:关注
            @Field("pid") int pid//二级评论的id

    );
}
